package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginUserTest {

    private UserClient userClient;
    private UserData userData;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userData = UserData.getRandom();
    }

    @After
    public void tearDown() {

        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Successful authorization with all fields")
    public void successfulAuthorization() {

        userClient.create(userData);
        ValidatableResponse response = userClient.login(new User(userData.email, userData.password));

        int statusCodeResponse = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");
        accessToken = response.extract().path("accessToken");
        String refreshToken = response.extract().path("refreshToken");
        String actualEmail = response.extract().path("user.email");
        String actualName = response.extract().path("user.name");
        //  assertThat("Status code is not correct", statusCodeResponse, equalTo(200));
        assertTrue("User not logged in", isUserCreated);
        assertNotNull(accessToken);
        assertNotNull(refreshToken);
        assertThat("Пользователь авторизовался под другим email", actualEmail, equalTo(userData.email));
        assertThat("Пользователь авторизовался под другим name", actualName, equalTo(userData.name));
    }

    @Test
    @DisplayName("Unsuccessful authorization by user, do not really email and password fields")
    public void unsuccessfulAuthorization() {

        ValidatableResponse response = userClient.login(User.getWithDoNotReallyEmailAndPassword(userData));
        int statusCodeResponse = response.extract().statusCode();
        boolean isUserUnLogged = response.extract().path("success");
        String message = response.extract().path("message");
        assertThat(statusCodeResponse, equalTo(401));
        assertFalse(isUserUnLogged);
        assertThat(message, equalTo("email or password are incorrect"));
    }
}