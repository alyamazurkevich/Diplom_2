package Order;

import practicum.order.OrderChecking;
import practicum.order.OrderStep;
import practicum.user.User;
import practicum.user.UserChecking;
import practicum.user.UserStep;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {
    private String accessToken;
    private OrderStep orderStep;
    private UserStep userStep;
    private OrderChecking orderChecking;
    private UserChecking userChecking;
    private User user;


    @Before
    public void setUp() {
        orderStep = new OrderStep();
        userStep= new UserStep();
        orderChecking = new OrderChecking();
        userChecking = new UserChecking();

        user = User.random();
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkCreatedSuccessfully(createResponse);
        accessToken = userStep.getAccessToken(createResponse);
    }

    @After
    public void tearDown() {
        if (accessToken !=null){
            userStep.deleteUser(accessToken);
        }
    }
    // авторизация пользователя при получении заказов
    @DisplayName("getting orders auth user")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void getAuthUserOrders() {
        ValidatableResponse listResponse = orderStep.getOrdersAuthUser(accessToken);
        orderChecking.checkCorrectOrders(listResponse);
    }

    // получать неавторизованные пользовательские заказы
    @DisplayName("get not auth user orders")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void getNotAuthUserOrders() {
        ValidatableResponse listResponse = orderStep.getOrdersNotAuthUser();
        orderChecking.checkIncorrectOrders(listResponse);
    }
}