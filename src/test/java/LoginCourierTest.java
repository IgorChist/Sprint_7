import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {

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
    public void shouldLoginedAndReturnId() {
        courierSteps
                .createCourier(courier);

        courierSteps
                .login(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void courierCannotLoginedWithoutLogin() {
        String correctLogin = courier.getLogin();
        courierSteps
                .createCourier(courier);
        courier.setLogin(null);

        courierSteps
                .login(courier)
                .statusCode(400)
                .body("message", notNullValue());
        courier.setLogin(correctLogin);
    }

    @Test
    public void ccourierCannotLoginedWithoutPassword() { // приходит ответ: Service unavailable HTTP/1.1 504 Gateway time out
        String correctPassword = courier.getPassword();
        courierSteps
                .createCourier(courier);
        courier.setPassword(null);

        courierSteps
                .login(courier)
                .statusCode(400) // приходит ответ: Service unavailable HTTP/1.1 504 Gateway time out
                .body("message", notNullValue());
        courier.setLogin(correctPassword);
    }

    @Test
    public void courierCannotLoginedWithIncorrectLogin() {
        String correctLogin = courier.getLogin();
        courierSteps
                .createCourier(courier);
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));

        courierSteps
                .login(courier)
                .statusCode(404)
                .body("message", notNullValue());

        courier.setLogin(correctLogin);
    }
    @Test
    public void courierCannotLoginedWithIncorrectPassword() {
        String correctPassword = courier.getPassword();
        courierSteps
                .createCourier(courier);
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));

        courierSteps
                .login(courier)
                .statusCode(404)
                .body("message", notNullValue());

        courier.setPassword(correctPassword);
    }
    @Test
    public void courierCannotLoginedWithoutRegistration() {

        courierSteps
                .login(courier)
                .statusCode(404)
                .body("message", notNullValue());

    }

    @After
    public void tearDown() {
        Integer id = courierSteps.login(courier)
                .extract().body().path("id");
        if (id != null) {
            courier.setId(id);
            courierSteps.delete(courier);
        }
    }
}