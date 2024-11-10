package User;

import practicum.user.User;
import practicum.user.UserChecking;
import practicum.user.UserStep;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreationUserTest {
    private String accessToken;
    private final UserStep userStep = new UserStep();
    UserChecking userChecking = new UserChecking();
    User user;

    @Before
    public void setUp() {
        user = User.random();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userStep.deleteUser(accessToken);
        }
    }
    // Успешное создание уникальной пользовательской проверки
    @DisplayName("Successfully creation of unique user check")
    @Description("POST/ api/ auth/ register")
    @Test
    public void createUserTest() {
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkCreatedSuccessfully(createResponse);
        accessToken = userStep.getAccessToken(createResponse);
    }
    // Не удается создать существующего пользователя
    @DisplayName("Can not create existing user")
    @Description("POST/ api/ auth/ register")
    @Test
    public void createSameUserTest() {
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkCreatedSuccessfully(createResponse);

        ValidatableResponse doubleCreateResponse = userStep.createUser(user);
        userChecking.checkNotCreatedSameUser(doubleCreateResponse);
        accessToken = userStep.getAccessToken(createResponse);
    }
    // Невозможно создать пользователя без поля имени
    @DisplayName("Can not create user without name field")
    @Description("POST/ api/ auth/ register")
    @Test
    public void createUserWithoutNameField() {
        var user = User.random();
        user.setName("");
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkNotCreatedField(createResponse);
    }
    // Невозможно создать пользователя без поля почты
    @DisplayName("Can not create user without mail field")
    @Description("POST/ api/ auth/ register")
    @Test
    public void createUserWithoutMailField() {
        var user = User.random();
        user.setEmail("");
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkNotCreatedField(createResponse);
    }

    // Невозможно создать пользователя без поля пароля
    @DisplayName("Can not create user without password field")
    @Description("POST/ api/ auth/ register")
    @Test
    public void createUserWithoutPasswordField() {
        var user = User.random();
        user.setPassword("");
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkNotCreatedField(createResponse);
    }
}