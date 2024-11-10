package User;

import practicum.user.User;
import practicum.user.UserChecking;
import practicum.user.UserStep;
import practicum.user.UserAccountData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
    private String accessToken;
    User user;
    private final UserStep userStep = new UserStep();
    UserChecking userChecking = new UserChecking();

    @Before
    public void setUp() {
        user = User.random();
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkCreatedSuccessfully(createResponse);
        accessToken = userStep.getAccessToken(createResponse);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userStep.deleteUser(accessToken);
        }
    }
    // Пользователь может войти в систему с правильными параметрами
    @DisplayName("User can logg in with correct params")
    @Description("POST/api/auth/login")
    @Test
    public void canLoggInWithCorrectParams() {
        var creds = UserAccountData.from(user);
        ValidatableResponse loginResponse = userStep.loginUser((UserAccountData) creds);
        userChecking.checkLoginSuccessfully(loginResponse);
    }
    // Пользователь не может войти в систему с неправильным адресом электронной почты
    @DisplayName("User can't logg in with wrong mail")
    @Description("POST/api/auth/login")
    @Test
    public void canNotLoggInWithWrongMail() {
        user.setEmail("0000");
        var creds = UserAccountData.from(user);
        ValidatableResponse loginResponse = userStep.loginUser((UserAccountData) creds);
        userChecking.checkNotLoginWithWrongParams(loginResponse);
    }
    // Пользователь не может войти в систему с неправильным адресом электронной почты
    @DisplayName("User can't logg in with wrong mail")
    @Description("POST/api/auth/login")
    @Test
    public void canNotLoggInWithWrongPassword() {
        user.setPassword("0000");
        var creds = UserAccountData.from(user);
        ValidatableResponse loginResponse = userStep.loginUser((UserAccountData) creds);
        userChecking.checkNotLoginWithWrongParams(loginResponse);
    }
}