package practicum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserChecking {
    // Проверка пользователя
    @Step("Check user create")
    public void checkCreatedSuccessfully(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }
    // Проверка пользовательского логина
    @Step("Check user login successfully")
    public void checkLoginSuccessfully(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }
    // Проверка не удается ли создать одного и того же пользователя
    @Step("Check can not create same user")
    public void checkNotCreatedSameUser(ValidatableResponse createSecondUserResponse) {
        createSecondUserResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }
    // Проверка не позволяет создать пользователя без обязательных полей
    @Step("Check can not create user without required fields")
    public void checkNotCreatedField(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))

                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }
    // Проверка не удается ли войти в систему с неправильными параметрами
    @Step("Check can not login with wrong params")
    public void checkNotLoginWithWrongParams(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    // Проверка изменения пользовательских данных
    @Step("Check of change user data")
    public void checkChange(ValidatableResponse changeResponse) {
        changeResponse
                .assertThat()
                .body("success", equalTo(true));
    }
    // Проверка что пользователь не может изменять данные
    @Step("Check not auth user can not change data")
    public void checkChangeData(ValidatableResponse changeResponse) {
        changeResponse
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}