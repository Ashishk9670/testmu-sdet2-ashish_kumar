package api;

import api.specs.ResponseSpecFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Epic("E-commerce API")
@Feature("Product API")
public class ProductApiTest {

    @Test(groups = {"api", "contract"})
    public void productSchemaIsValid() {
        new ProductApi().fetchProduct(1)
                .then()
                .spec(ResponseSpecFactory.status(200))
                .body(matchesJsonSchema(new File("src/main/resources/schemas/product-schema.json")));
    }

    @Test(groups = {"api", "smoke"})
    public void productSearchReturnsResults() {
        int productCount = new ProductApi().searchProducts("computer").jsonPath().getList("$").size();

        Assertions.assertThat(productCount).isPositive();
    }

    @Test(groups = {"api", "negative"})
    public void unknownRouteReturnsNotFound() {
        new APIClient().get("/unknown-route")
                .then()
                .spec(ResponseSpecFactory.noContentTypeStatus(404));
    }
}
