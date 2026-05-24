package api;

import api.specs.ResponseSpecFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;

public class OrderApi {
    private final APIClient client = new APIClient();

    @Step("Create order")
    public Response createOrder(Order order) {
        return client.post("/carts", order)
                .then()
                .spec(ResponseSpecFactory.statusAnyOf(200, 201))
                .extract()
                .response();
    }

    @Step("Fetch order: {orderId}")
    public Response fetchOrder(int orderId) {
        return client.get("/carts/" + orderId);
    }

    @Step("Update order: {orderId}")
    public Response updateOrder(int orderId, Order order) {
        return client.put("/carts/" + orderId, order);
    }

    @Step("Cancel order: {orderId}")
    public Response cancelOrder(int orderId) {
        return client.delete("/carts/" + orderId);
    }
}
