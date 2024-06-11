import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.*;

public class NegativeCreateCourierTest {

    private CourierSteps courierSteps = new CourierSteps();
    Courier courier;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    public void cannotCreateCourierWithoutLogin() {
        courier.setLogin(null);

        courierSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", notNullValue());
    }

    @Test
    public void cannotCreateCourierWithoutPassword() {
        courier.setPassword(null);

        courierSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", notNullValue());
    }
}
