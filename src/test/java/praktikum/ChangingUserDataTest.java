package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ChangingUserDataTest {

    private UserClient userClient;
    private UserData user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserData.getRandom();
        ValidatableResponse responseCreatedUser = userClient.create(user);
        accessToken = responseCreatedUser.extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Change user email address, password and nameall data with login")
    public void changeDataUserEmailNamePasswordWithLogin() {

        UserData newUserData = UserData.getRandom();
        ValidatableResponse responseChangeData = userClient.changeData(newUserData, accessToken);
        String actualEmail = responseChangeData.extract().path("user.email");
        String actualName = responseChangeData.extract().path("user.name");
        ValidatableResponse responseLoginWithNewData = userClient.login(new User(newUserData.email, newUserData.password));
        int statusCodeResponseChangeData = responseChangeData.extract().statusCode();
        int statusCodeResponseLoginWithNewData = responseLoginWithNewData.extract().statusCode();
        assertThat(statusCodeResponseChangeData, equalTo(200));
        assertThat("У пользователя не изменились данные email", actualEmail, equalTo(newUserData.email));
        assertThat("У пользователя не изменились данные name", actualName, equalTo(newUserData.name));
        assertThat(statusCodeResponseLoginWithNewData, equalTo(200));
    }

    @Test
    @DisplayName("Change user email with email field with login")
    public void changeDataUserEmailWithLogin() {

        UserData newUserData = UserData.getEmail();
        ValidatableResponse responseChangeData = userClient.changeData(newUserData, accessToken);
        String actualEmail = responseChangeData.extract().path("user.email");
        String actualName = responseChangeData.extract().path("user.name");
        ValidatableResponse responseLoginWithNewData = userClient.login(new User(newUserData.email, user.password));
        int statusCodeResponseChangeData = responseChangeData.extract().statusCode();
        int statusCodeResponseLoginWithNewData = responseLoginWithNewData.extract().statusCode();
        assertThat(statusCodeResponseChangeData, equalTo(200));
        assertThat("Данные email не изменились", actualEmail, equalTo(newUserData.email));
        assertThat("Данные name не изменились", actualName, equalTo(user.name));
        assertThat(statusCodeResponseLoginWithNewData, equalTo(200));
    }

    @Test
    @DisplayName("Change user password with login")
    public void changeDataUserPasswordWithLogin() {
        UserData newUserData =  UserData.getPassword();
        ValidatableResponse responseChangeData = userClient.changeData(newUserData, accessToken);
        String actualEmail = responseChangeData.extract().path("user.email");
        String actualName = responseChangeData.extract().path("user.name");
        ValidatableResponse responseLoginWithNewData = userClient.login(new User(user.email, newUserData.password));
        int statusCodeResponseChangeData = responseChangeData.extract().statusCode();
        int statusCodeResponseLoginWithNewData = responseLoginWithNewData.extract().statusCode();
        assertThat(statusCodeResponseChangeData, equalTo(200));
        assertThat("Данные email не изменились", actualEmail, equalTo(user.email));
        assertThat("Данные name не изменились", actualName, equalTo(user.name));
        assertThat(statusCodeResponseLoginWithNewData, equalTo(200));
    }

    @Test
    @DisplayName("Change user name with login")
    public void changeDataUserNameWithLogin() {
        UserData newUserData =  UserData.getName();
        ValidatableResponse responseChangeData = userClient.changeData(newUserData, accessToken);
        String actualEmail = responseChangeData.extract().path("user.email");
        String actualName = responseChangeData.extract().path("user.name");
        ValidatableResponse responseLoginWithNewData = userClient.login(new User(user.email, user.password));
        int statusCodeResponseChangeData = responseChangeData.extract().statusCode();
        int statusCodeResponseLoginWithNewData = responseLoginWithNewData.extract().statusCode();
        assertThat(statusCodeResponseChangeData, equalTo(200));
        assertThat("Данные email не изменились", actualEmail, equalTo(user.email));
        assertThat("Данные name не изменились", actualName, equalTo(newUserData.name));
        assertThat(statusCodeResponseLoginWithNewData, equalTo(200));
    }

    @Test
    @DisplayName("Change user data, email address, password and name, without login")
    public void ChangeDataUserEmailNamePasswordWithoutLogin() {
        UserData newUserData = UserData.getRandom();
        ValidatableResponse responseChangeDataWithoutToken = userClient.changeDataWithoutToken(newUserData);
        boolean isEmail = responseChangeDataWithoutToken.extract().path("success");
        String message = responseChangeDataWithoutToken.extract().path("message");
        ValidatableResponse responseLoginWithNewData = userClient.login(new User(newUserData.email, newUserData.password));
        int statusCodeResponseChangeData = responseChangeDataWithoutToken.extract().statusCode();
        int statusCodeResponseLoginWithNewData = responseLoginWithNewData.extract().statusCode();
        assertThat(statusCodeResponseChangeData, equalTo(401));
        assertFalse(isEmail);
        assertThat(message, equalTo("You should be authorised"));
        assertThat(statusCodeResponseLoginWithNewData, equalTo(401));
    }
}
