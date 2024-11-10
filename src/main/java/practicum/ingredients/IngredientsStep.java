package practicum.ingredients;

import io.qameta.allure.Step;
import practicum.Parameters;
import practicum.Urls;
import static practicum.Urls.BASE_URI;
import static io.restassured.RestAssured.given;

public class IngredientsStep extends Parameters {
    // Получение списка ингредиентов
    @Step("Getting a list of ingredients")
    public IngredientsOrder getIngredients() {
        return spec()
                .get(Urls.INGREDIENTS)
                .then().log().all()
                .extract()
                .body()
                .as(IngredientsOrder.class);
    }
}
