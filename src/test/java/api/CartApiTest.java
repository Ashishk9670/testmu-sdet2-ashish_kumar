package api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Epic("E-commerce API")
@Feature("Cart API")
public class CartApiTest {

    @Test(groups = {"api", "smoke"})
    public void addCartItemReturnsCartIdentifier() {
        Response response = new CartApi().addCartItem(1, 1, 1);

        Assertions.assertThat(response.jsonPath().getInt("id")).isPositive();
    }
}
