package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CreateUserWithEmptyFieldTest {

    private UserClient userClient;
    UserData user;
    private int expectedStatus;
    private boolean expectedSuccess;
    private String expectedErrorTextMessage;

    public CreateUserWithEmptyFieldTest(UserData user, int expectedStatus, boolean expectedSuccess, String expectedErrorTextMessage) {
        this.user = user;
        this.expectedStatus = expectedStatus;
        this.expectedSuccess = expectedSuccess;
        this.expectedErrorTextMessage = expectedErrorTextMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserData.getWithEmailAndPassword(), 403, false, "Email, password and name are required fields"},
                {UserData.getWithPasswordAndName(), 403, false, "Email, password and name are required fields"},
                {UserData.getWithEmailAndName(), 403, false, "Email, password and name are required fields"}
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Сheck the creation of a user without one field")
    public void createUsersWithoutField() {

        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");
        String errorTextMessage = response.extract().path("message");
        assertThat(statusCode, equalTo(expectedStatus));
        assertThat(actualSuccess, equalTo(expectedSuccess));
        assertThat(errorTextMessage, equalTo(expectedErrorTextMessage));
    }
}
