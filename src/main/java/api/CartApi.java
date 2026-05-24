package api;

import api.specs.ResponseSpecFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

public class CartApi {
    private final APIClient client = new APIClient();

    @Step("Add item to cart")
    public Response addCartItem(int userId, int productId, int quantity) {
        Map<String, Object> body = Map.of(
                "userId", userId,
                "products", new Object[]{Map.of("productId", productId, "quantity", quantity)}
        );
        return client.post("/carts", body)
                .then()
                .spec(ResponseSpecFactory.statusAnyOf(200, 201))
                .extract()
                .response();
    }
}
