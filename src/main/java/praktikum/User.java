package praktikum;

import com.github.javafaker.Faker;

public class User {

    public String email;
    public String password;
    public String name;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public static User getWithDoNotReallyEmailAndPassword(UserData user) {
        Faker faker = new Faker();
        return new User().setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password(6, 10));
    }
}
