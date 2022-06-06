package praktikum;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.javafaker.Faker;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserData {

    public String email;
    public String password;
    public String name;

    public UserData() {    }

    public UserData(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public UserData setEmail (String email) {
        this.email = email;
        return this;
    }

    public UserData setPassword (String password) {
        this.password = password;
        return this;
    }

    public UserData setName (String name) {
        this.name = name;
        return this;
    }
    public static UserData getEmail() {
        Faker faker = new Faker();
        return new UserData().setEmail(faker.internet().emailAddress());
    }

    public static UserData getPassword() {
        Faker faker = new Faker();
        return new UserData().setPassword(faker.internet().password(6,10));
    }

    public static UserData getName() {
        Faker faker = new Faker();
        return new UserData().setName(faker.name().firstName());
    }

    public static UserData getRandom() {
        Faker faker = new Faker();

        final String emailUser = faker.internet().emailAddress();
        final String passwordUser = faker.internet().password(6, 10);
        final String nameUser = faker.name().firstName();
        return new UserData(emailUser, passwordUser, nameUser);
    }

    public static UserData getWithPasswordAndName() {
        Faker faker = new Faker();
        return new UserData().setPassword(faker.internet().password(6, 10))
                .setName(faker.name().firstName());
    }
    public static UserData getUserWithEmail(UserData userData) {
        return new UserData().setEmail(userData.email);
    }

    public static UserData getWithEmailAndName() {
        Faker faker = new Faker();
        return new UserData().setEmail(faker.internet().emailAddress())
                .setName(faker.name().firstName());
    }
    public static UserData getUserWithPassword (UserData userData) {
        return new UserData().setPassword(userData.password);
    }
    public static UserData getUserWithName (UserData userData) {
        return new UserData().setName(userData.name);
    }


    public static UserData getWithEmailAndPassword() {
        Faker faker = new Faker();
        return new UserData().setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password(6, 10));
    }



}
