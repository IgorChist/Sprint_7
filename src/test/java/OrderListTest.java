import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Test;
import steps.OrderSteps;


import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    @Test
    public void shouldGetOrdersList() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        OrderSteps orderSteps = new OrderSteps();

        orderSteps.getListOrders()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}