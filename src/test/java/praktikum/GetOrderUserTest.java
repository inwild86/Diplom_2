package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class GetOrderUserTest {

    private UserClient userClient;
    private UserData user;
    String accessToken;
    private Ingredients ingredients;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserData.getRandom();
        orderClient = new OrderClient();
        ingredients = Ingredients.getRandom();
    }

    @After
    public void tearDown() {
        if (accessToken != "") {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Get orders from logged in user")

    public void getOrderWithLogin() {

        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseOrder = orderClient.userOrderInfo(accessToken);
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        boolean isGetOrders = responseOrder.extract().path("success");
        assertThat(statusCodeResponseOrder, equalTo(200));
        assertTrue("Заказ не получен", isGetOrders);
    }

    @Test
    @DisplayName("Get orders from  unregistered user")
    public void getOrdersWithoutLogin() {

        ValidatableResponse responseOrder = orderClient.userOrderInfoWithoutToken();
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        boolean isGetOrders = responseOrder.extract().path("success");
        String message = responseOrder.extract().path("message");
        assertThat(statusCodeResponseOrder, equalTo(401));
        assertFalse("Операция успешна", isGetOrders);
        assertThat(message, equalTo("You should be authorised"));
    }
}