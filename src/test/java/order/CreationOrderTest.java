package Order;

import practicum.ingredients.IngredientsStep;
import practicum.ingredients.OrderIngredients;
import practicum.order.*;
import practicum.order.Order;
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
import java.util.ArrayList;
import java.util.List;


public class CreationOrderTest {
    private String accessToken;
    private OrderStep orderStep;
    private UserStep userStep;
    private OrderChecking orderChecking;
    private UserChecking userChecking;
    private User user;

    private IngredientsStep ingredientsStep;
    private List<String> ingredientIds;
    private Order order;


    @Before
    public void setUp() {
        orderStep = new OrderStep();
        userStep= new UserStep();
        orderChecking = new OrderChecking();
        userChecking = new UserChecking();
        ingredientsStep = new IngredientsStep();


        user = User.random();
        ValidatableResponse createResponse = userStep.createUser(user);
        userChecking.checkCreatedSuccessfully(createResponse);
        accessToken = userStep.getAccessToken(createResponse);
        ingredientIds = new ArrayList<>();
        order = new Order(ingredientIds);
    }
    @After
    public void tearDown(){
        if (accessToken !=null){
            userStep.deleteUser(accessToken);
        }
    }
    // Создание заказа с использованием правильных ингредиентов
    @DisplayName("Creating order with correct ingredients")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void createOrderCorrectHashAuth(){
        OrderIngredients orderIngredients = ingredientsStep.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id());
        ingredientIds.add(orderIngredients.getData().get(1).get_id());
        ValidatableResponse createOrderResponse = orderStep.createOrder(order,accessToken);
        orderChecking.checkCreatedOrder(createOrderResponse);
    }
    // Создание заказа с правильными ингредиентами без авторизации
    @DisplayName("Creating order with correct ingredients without auth")
    @Description("POST/api/orders")
    @Test
    public void createOrderCorrectHashNotAuth(){
        OrderIngredients orderIngredients = ingredientsStep.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id());
        ingredientIds.add(orderIngredients.getData().get(1).get_id());
        ValidatableResponse orderResponse = orderStep.createOrderWithoutAuth(order);
        orderChecking.checkCreatedOrder(orderResponse);
    }
    // Создание заказа с неправильными ингредиентами с авторизацией пользователя
    @DisplayName("Creating order with incorrect ingredients auth user")
    @Description("POST/api/orders")
    @Test
    public void createOrderIncorrectHashAuth(){
        OrderIngredients orderIngredients = ingredientsStep.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id()+"000");
        ingredientIds.add(orderIngredients.getData().get(1).get_id()+"000");
        ValidatableResponse orderResponse = orderStep.createOrder(order,accessToken);
        orderChecking.checkBadHash(orderResponse);
    }
    // Создание заказа с неправильными ингредиентами без авторизации пользователя
    @DisplayName("Creating order with incorrect ingredients not auth user")
    @Description("POST/api/orders")
    @Test
    public void createOrderIncorrectHashNotAuth(){
        OrderIngredients orderIngredients = ingredientsStep.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id()+"000");
        ingredientIds.add(orderIngredients.getData().get(1).get_id()+"000");
        ValidatableResponse orderResponse = orderStep.createOrderWithoutAuth(order);
        orderChecking.checkBadHash(orderResponse);
    }
    // Создание заказа без указания ингредиентов пользователем
    @DisplayName("Creating order with no ingredients auth user")
    @Description("POST/api/orders")
    @Test
    public void createOrderNoHashAuth(){
        ValidatableResponse orderResponse = orderStep.createOrder(order,accessToken);
        orderChecking.checkNoHash(orderResponse);
    }
    // Создание заказа без ингредиентов без авторизации пользователя
    @DisplayName("Creating order with no ingredients not auth user")
    @Description("POST/api/orders")
    @Test
    public void createOrderNoHashNotAuth(){
        ValidatableResponse orderResponse = orderStep.createOrderWithoutAuth(order);
        orderChecking.checkNoHash(orderResponse);
    }
}