package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Configuration {

    @Step
    public ValidatableResponse createOrder(Ingredients ingredients, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post(EndPoints.ORDERS)
                .then();
    }

    @Step
    public ValidatableResponse userOrderInfo(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .when()
                .get(EndPoints.ORDERS)
                .then();
    }

    @Step
    public ValidatableResponse userOrderInfoWithoutToken() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(EndPoints.ORDERS)
                .then();
    }
}