package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CreateUserTest {

    private UserClient userClient;
    private UserData user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserData.getRandom();
    }

    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Сheck the successful creation of a user")
    public void сreateUser() {

        ValidatableResponse response = userClient.create(user);
        int statusCodePositiveResponseCreate = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");
        ValidatableResponse responseUserLogged = userClient.login(new User(user.email, user.password));
        String refreshToken = responseUserLogged.extract().path("refreshToken");
        accessToken = responseUserLogged.extract().path("accessToken");
        assertThat(statusCodePositiveResponseCreate, equalTo(200));
        assertTrue("User is not created", isUserCreated);
        assertNotNull("Пустой accessToken", accessToken);
        assertNotNull("Пустой refreshToken", refreshToken);
    }

    @Test
    @DisplayName("Сheck the unsuccessful creation of a user twice")

    public void сreateTwoIdenticalUsers() {

        userClient.create(user);
        ValidatableResponse response = userClient.create(user);
        int statusCodeNegativeResponse = response.extract().statusCode();
        boolean isSuccess = response.extract().path("success");
        String message = response.extract().path("message");
        assertThat(statusCodeNegativeResponse, equalTo(403));
        assertFalse(isSuccess);
        assertThat("Создан ещё один пользователь с теми же данными", message, (equalTo("User already exists")));
    }
}
