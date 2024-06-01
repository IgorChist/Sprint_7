package steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static config.Config.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    public ValidatableResponse createOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then();
    }

    public ValidatableResponse getListOrders() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .when()
                .get(LIST_ORDERS)
                .then();
    }
}