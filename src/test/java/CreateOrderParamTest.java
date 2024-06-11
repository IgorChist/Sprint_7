import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParamTest {
    private String firstColour;
    private String secondColour;

    @Parameterized.Parameters
    public static Object[][] colour() {
        return new Object[][]{
                {null, null},
                {"BLACK", null},
                {null, "GREY"},
                {"BLACK", "GREY"}
        };
    }

    public CreateOrderParamTest(String firstColour, String secondColour) {
        this.firstColour = firstColour;
        this.secondColour = secondColour;
    }

    @Test
    public void shouldCreateOrder() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        OrderSteps orderSteps = new OrderSteps();
        String[] testColor = {firstColour, secondColour};
        Order order = new Order("Олег",
                "Великий",
                "Усачева, 16",
                4,
                "89998887766",
                2,
                "2024-06-13",
                "123",
                testColor);

        orderSteps.createOrder(order)
                .statusCode(201)
                .body("track", notNullValue());

    }
}
