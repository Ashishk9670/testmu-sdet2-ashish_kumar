package api;

import api.specs.ResponseSpecFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import models.CartItem;
import models.Order;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Epic("E-commerce API")
@Feature("Order API")
public class OrderApiTest {

    @Test(groups = {"api", "regression"})
    public void createFetchUpdateAndCancelOrder() {
        OrderApi orderApi = new OrderApi();
        Order order = new Order(null, 1, List.of(new CartItem(1, 1)), "CREATED");

        Response created = orderApi.createOrder(order);
        int orderId = created.jsonPath().getInt("id");
        Assertions.assertThat(orderId).isPositive();

        orderApi.fetchOrder(orderId).then().spec(ResponseSpecFactory.status(200));
        orderApi.updateOrder(orderId, new Order(orderId, 1, List.of(new CartItem(1, 2)), "UPDATED"))
                .then()
                .spec(ResponseSpecFactory.status(200));
        orderApi.cancelOrder(orderId).then().spec(ResponseSpecFactory.status(200));
    }

    @Test(groups = {"api", "contract"})
    public void orderSchemaIsValid() {
        new OrderApi().fetchOrder(1)
                .then()
                .spec(ResponseSpecFactory.status(200))
                .body(matchesJsonSchema(new File("src/main/resources/schemas/order-schema.json")));
    }

    @Test(groups = {"api", "negative"})
    public void invalidPayloadIsRejected() {
        new AuthApi().loginRaw(new models.AuthRequest("mor_2314", null))
                .then()
                .statusCode(org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.is(400),
                        org.hamcrest.Matchers.is(401)
                ));
    }
}
