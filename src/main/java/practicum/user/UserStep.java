package practicum.user;
import io.qameta.allure.Step;

import static practicum.Urls.USER;
import static practicum.Urls.LOGIN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static practicum.Urls.*;
import practicum.Parameters;

import io.restassured.response.ValidatableResponse;

public class UserStep extends Parameters {
    @Step("Create correct user")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }
    // Получить токен доступа
    @Step("Get access token")
    public String getAccessToken(ValidatableResponse createResponse) {
        return createResponse
                .extract()
                .path("accessToken");
    }
    // Удалить пользователя
    @Step("Delete user")
    public void deleteUser(String accessToken) {
        spec()
                .header("authorization", accessToken)
                .when()
                .delete(USER)
                .then().log().all();
    }
    // Логин Пользователя
    @Step("Login user")
    public ValidatableResponse loginUser(UserAccountData creds) {
        return spec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }
    // Изменение пользовательских данных
    @Step("Changing user data")
    public ValidatableResponse changeUserData(String accessToken, User user) {
        return spec()
                .header("authorization", accessToken)
                .body(user)
                .when()
                .patch(USER)
                .then()
                .log().all();
    }
    // Изменение неавторизованных пользовательских данных
    @Step("Changing not auth user data")
    public ValidatableResponse changeUserData(User user) {
        return spec()
                .body(user)
                .when()
                .patch(USER)
                .then()
                .log().all();
    }
}