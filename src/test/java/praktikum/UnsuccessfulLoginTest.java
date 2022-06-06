package praktikum;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

//@RunWith(Parameterized.class)
public class UnsuccessfulLoginTest {
   /* private UserClient userClient = new UserClient();
    private String accessToken;

    private final int expectedStatus;
    private final String expectedErrorMessage;
    private static final UserData user= UserData.getRandom();
    private static final UserData userData;

    public UnsuccessfulLoginTest (UserData userData, int expectedStatus, String expectedErrorMessage){
        this.userData = userData;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData () {
        return new Object[][] {
                {UserData.getUserWithEmail(userData), 401, "email or password are incorrect"},
                {UserData.getUserWithPassword(userData), 401, "email or password are incorrect"},
                {UserData.getUserWithName(userData), 401, "email or password are incorrect"},
                {UserData.getWithEmailAndPassword(), 401, "email or password are incorrect"}
        };
    }


    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Unsuccessful authorization by user")
    @Description("User authorization with do not really email and password fields")
    public void unsuccessfulAuthorizationWithDoNotReallyEmailAndPassword() {

        ValidatableResponse response = userClient.login(User.getWithDoNotReallyEmailAndPassword(userData));
        int statusCodeResponse = response.extract().statusCode();
        boolean isUserUnLogged = response.extract().path("success");
        String message = response.extract().path("message");
        assertThat(statusCodeResponse, equalTo(401));
        assertFalse(isUserUnLogged);
        assertThat(message, equalTo("email or password are incorrect"));
    }*/

}
