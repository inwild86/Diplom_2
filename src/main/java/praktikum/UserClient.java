package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Configuration {

    @Step
    public ValidatableResponse create(UserData userData) {
        return given()
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .post(EndPoints.USER_CREATE)
                .then();
    }

    @Step
    public ValidatableResponse login(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(EndPoints.USER_LOGIN)
                .then();
    }

    @Step
    public void delete(String accessToken) {
        if (accessToken == null) {
            return;
        }
        given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .when()
                .delete(EndPoints.USER)
                .then()
                .statusCode(202);
    }

    @Step
    public ValidatableResponse changeData(UserData userData, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .patch(EndPoints.USER)
                .then();
    }

    @Step
    public ValidatableResponse changeDataWithoutToken(UserData userData) {
        return given()
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .patch(EndPoints.USER)
                .then();
    }
}
