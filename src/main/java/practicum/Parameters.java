package practicum;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static practicum.Urls.BASE_URI;
import static io.restassured.RestAssured.given;

public class Parameters {
    public static RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI);
    }
}