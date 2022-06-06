package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class CreateOrderTest {

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
    @DisplayName("Create order without login, with ingredients")
    public void createOrderWithIngredientsWithoutLogin() {

        accessToken = "";
        ValidatableResponse responseOrder = orderClient.createOrder(ingredients, accessToken);
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        boolean isOrderCreated = responseOrder.extract().path("success");
        int orderNumber = responseOrder.extract().path("order.number");
        assertThat(statusCodeResponseOrder, equalTo(200));
        assertTrue("Order is not created", isOrderCreated);
        assertNotNull("Пустой номер заказа", orderNumber);
    }

    @Test
    @DisplayName("Create order with login, with ingredients")
    public void createOrderWithIngredientsWithLogin() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseOrder = orderClient.createOrder(ingredients, accessToken);
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        boolean isOrderCreated = responseOrder.extract().path("success");
        int orderNumber = responseOrder.extract().path("order.number");
        assertThat(statusCodeResponseOrder, equalTo(200));
        assertTrue("Order is not created", isOrderCreated);
        assertNotNull("Пустой номе заказа", orderNumber);
    }

    @Test
    @DisplayName("Create order without ingredients and with login")
    public void CreateOrderWithoutReallyIngredientsWithLogin() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseOrder = orderClient.createOrder(Ingredients.getNotReallyIngredients(), accessToken);
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        assertThat(statusCodeResponseOrder, equalTo(500));
    }

    @Test
    @DisplayName("Create order without ingredients and with login")
    public void сreateOrderWithoutIngredientsWithLogin() {

        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseOrder = orderClient.createOrder(Ingredients.getWithoutIngredients(), accessToken);
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        boolean isOrderCreated = responseOrder.extract().path("success");
        String orderMessage = responseOrder.extract().path("message");
        assertThat(statusCodeResponseOrder, equalTo(400));
        assertFalse("Order is created", isOrderCreated);
        assertThat(orderMessage, equalTo("Ingredient ids must be provided"));
    }
}
