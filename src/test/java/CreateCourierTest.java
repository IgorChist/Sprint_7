import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class CreateCourierTest {
    private CourierSteps courierSteps = new CourierSteps();
    Courier courier;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter());

        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    public void shouldReturnOkTrue() {
        courierSteps
                .createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void cannotCreateTwoIdenticalCouriers() {
        courierSteps
                .createCourier(courier);
        courierSteps
                .createCourier(courier)
                .statusCode(409)
                .body("message", notNullValue());
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.login(courier)
                .extract().body().path("id");
        courier.setId(id);
        courierSteps.delete(courier);
    }
}
