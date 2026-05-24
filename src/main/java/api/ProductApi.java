package api;

import api.specs.ResponseSpecFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ProductApi {
    private final APIClient client = new APIClient();

    @Step("Search products by keyword: {keyword}")
    public Response searchProducts(String keyword) {
        return client.get("/products")
                .then()
                .spec(ResponseSpecFactory.status(200))
                .extract()
                .response();
    }

    @Step("Fetch product: {productId}")
    public Response fetchProduct(int productId) {
        return client.get("/products/" + productId);
    }
}
