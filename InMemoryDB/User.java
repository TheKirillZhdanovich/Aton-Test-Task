package InMemoryDB;

/*

Обычный Java класс, который будет представлять исходную структуру данных

 */

public class User {
    private Long account;
    private String name;
    private Double value;

    public User(Long account, String name, Double value) {
        this.account = account;
        this.name = name;
        this.value = value;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "User{" +
                "account=" + account +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
