package steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Courier;

import static config.Config.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {



    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then();
    }

    public ValidatableResponse login(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    public ValidatableResponse delete(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .pathParam("id", courier.getId())
                .when()
                .delete(DELETE_COURIER)
                .then();
    }
}
