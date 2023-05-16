package InMemoryDB;

import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException {
        /*
        Создаем нашу базу данных
         */
        DataBase db = new DataBase();

        /*
        Заполняем её
         */
        db.add(new User(3L, "bgas", 1.9));
        db.add(new User(1L, "sdfhshs", 2.9));
        db.add(new User(5L, "ggg", 3.76));
        db.add(new User(4L, "dsgas", 4.76));
        db.add(new User(2L, "cssdfhas", 5.76));

        /*
        Удаление записи
         */
        db.remove(new User(4L, "dsgas", 4.76));

        /*
        Update записи
         */
        db.update(new User(1L, "sdfhshs", 2.9), 9L, "kkkkkk", 0.8);

        /*
        Поиск записи по разным полям
         */
        System.out.println(db.findUserByName("ggg").toString());
        System.out.println(db.findUserByAccount(9L).toString());
        System.out.println(db.findUserByValue(5.76).toString());
    }
}
