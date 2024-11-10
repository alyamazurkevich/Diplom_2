package practicum.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static practicum.Urls.ORDERS;
import static practicum.Urls.BASE_URI;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

import practicum.Parameters;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderStep extends Parameters {
    // Создание заказа с авторизацией
    @Step("Creating order with auth")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return   spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }
    // Создание заказа без авторизации
    @Step("Creating order without auth")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return   spec()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    // Получение заказов от авторизованного пользователя
    @Step("Getting orders of  auth user")
    public ValidatableResponse getOrdersAuthUser(String accessToken) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .baseUri(BASE_URI)
                .when()
                .get(ORDERS)
                .then()
                .log().all();
    }
    // Получение заказов от неавторизованного пользователя
    @Step("Getting orders of not auth user")
    public ValidatableResponse getOrdersNotAuthUser() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDERS)
                .then()
                .log().all();
    }
}