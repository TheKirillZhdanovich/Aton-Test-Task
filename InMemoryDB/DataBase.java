package InMemoryDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*

Чтобы реализовать поиск по любому из полей за log(n), нам нужно применить бинарный поиск.
Ключевой момент это то, что данные должны быть отсортированы по искомому полю.
Поэтому, чтобы не сортировать каждый раз данные в методе (получим сложность поиска n*log(n))
придется завести 3 динамических массива такие, что они всегда будут отсортированы по одному из полей класса User.

Здесь я исходил из того, что все поля будут уникальны, поэтому и искал одну запись, но код можно легко переделать если
нам нужно получать массив из них.

*/

public class DataBase {

    /*
    Заводим компараторы для того, чтобы сравнивать объекты класса User между собой по разным полям.
     */
    private final Comparator<User> comparatorAccount = Comparator.comparingLong(User::getAccount);
    private final Comparator<User> comparatorName = Comparator.comparing(User::getName);
    private final Comparator<User> comparatorValue = Comparator.comparingDouble(User::getValue);

    /*
    Объявляем динамические массивы в которых мы и будем хранить объекты класса User.
    Данные в каждом из них всегда будут отсортированы по нужному полю.
     */
    private List<User> sortedByAccount = new ArrayList<>() {
        @Override
        public boolean add(User user) {
            int index = Collections.binarySearch(this, user, comparatorAccount);
            if (index < 0) index = ~index;
            super.add(index, user);
            return true;
        }
    };

    private List<User> sortedByName = new ArrayList<>() {
        @Override
        public boolean add(User user) {
            int index = Collections.binarySearch(this, user, comparatorName);
            if (index < 0) index = ~index;
            super.add(index, user);
            return true;
        }
    };

    private List<User> sortedByValue = new ArrayList<>() {
        @Override
        public boolean add(User user) {
            int index = Collections.binarySearch(this, user, comparatorValue);
            if (index < 0) index = ~index;
            super.add(index, user);
            return true;
        }
    };

    /*
    Добавление нового элемента в массивы
     */
    public void add(User user) {
        sortedByAccount.add(user);
        sortedByName.add(user);
        sortedByValue.add(user);
    }

    /*
    Удаление нужной записи
     */
    public void remove(User user) throws IOException {
        if (!sortedByAccount.remove(user) || !sortedByName.remove(user) || !sortedByValue.remove(user)) {
            throw new IOException("user doesn't exist");
        }
    }

    /*
    Для того чтобы сохранять массивы отсортированными, нужно сначала удалить запись, а потом добавить ее
    модифицированную версию.
     */
    public void update(User userToUpdate, Long newAccount, String newName, Double newValue) throws IOException {
        int index = Collections.binarySearch(sortedByAccount, userToUpdate, comparatorAccount);

        if (index < 0) {
            throw new IOException("user doesn't exist");
        }

        User user = sortedByAccount.get(index);

        sortedByAccount.remove(user);
        sortedByName.remove(user);
        sortedByValue.remove(user);

        user.setAccount(newAccount);
        user.setName(newName);
        user.setValue(newValue);

        sortedByAccount.add(user);
        sortedByName.add(user);
        sortedByValue.add(user);
    }

    /*
    Поиск по полю account за log(n) т.к. получение по индексу из динамического массива происходит за О(1)
     */
    public User findUserByAccount(Long account) throws IOException {
        int index = Collections.binarySearch(sortedByAccount, new User(account, null, null), comparatorAccount);

        if (index < 0) {
            throw new IOException("user doesn't exist");
        }

        return sortedByAccount.get(index);
    }

    /*
    Поиск по полю name за log(n)
     */
    public User findUserByName(String name) throws IOException {
        int index = Collections.binarySearch(sortedByName, new User(null, name, null), comparatorName);

        if (index < 0) {
            throw new IOException("user doesn't exist");
        }

        return sortedByName.get(index);
    }

    /*
    Поиск по полю value за log(n)
     */
    public User findUserByValue(Double value) throws IOException {
        int index = Collections.binarySearch(sortedByValue, new User(null, null, value), comparatorValue);

        if (index < 0) {
            throw new IOException("user doesn't exist");
        }

        return sortedByValue.get(index);
    }
}
