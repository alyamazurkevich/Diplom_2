package User;

import practicum.user.UserChecking;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import practicum.user.User;
import practicum.user.UserStep;
import practicum.user.UserAccountData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataUserTest {
    private String accessToken;
    User user;
    private final UserStep userStep = new UserStep();
    UserChecking userChecking = new UserChecking();
    @Before
    public void setUp(){
        user = User.random();
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkCreatedSuccessfully(createResponse);
        var creds = UserAccountData.from(user);
        ValidatableResponse loginResponse = userStep.loginUser((UserAccountData) creds);
        userChecking.checkLoginSuccessfully(loginResponse);
        accessToken = userStep.getAccessToken(loginResponse);
    }
    @After
    public void tearDown() {
        if (accessToken != null) {
            userStep.deleteUser(accessToken);
        }
    }
    // Изменение данных имени пользователя с авторизацией
    @DisplayName("Changing user name data with authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserNameAuthTest(){
        user.setPassword("Pers12!");
        ValidatableResponse changeResponse =  userStep.changeUserData(accessToken, user);
        userChecking.checkChange(changeResponse);
    }
    // Изменение данных электронной почты пользователя с авторизацией
    @DisplayName("Changing user email  data with authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserEmailAuthTest(){
        user.setEmail("persivald@yandex.ru");
        ValidatableResponse changeResponse =  userStep.changeUserData(accessToken, user);
        userChecking.checkChange(changeResponse);
    }
    // Изменение данных пароля пользователя с авторизацией
    @DisplayName("Changing user password  data with authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserPasswordAuthTest(){
        user.setPassword("1234");
        ValidatableResponse changeResponse =  userStep.changeUserData(accessToken, user);
        userChecking.checkChange(changeResponse);
    }
    // Изменение данных без авторизации
    @DisplayName("Changing data without authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserDataTest() {
        user.setEmail("persivald@yandex.ru");
        ValidatableResponse changeResponse = userStep.changeUserData(user);
        userChecking.checkChangeData(changeResponse);
    }
}