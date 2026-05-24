package integration;

import api.OrderApi;
import base.BaseTest;
import dataproviders.TestDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import models.Address;
import models.CartItem;
import models.Order;
import models.PaymentDetails;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.OrderHistoryPage;

import java.util.List;

@Epic("Hybrid E2E")
@Feature("API created order appears in UI")
public class OrderIntegrationTest extends BaseTest {

    @Test(groups = {"integration", "regression"}, dataProvider = "checkoutData", dataProviderClass = TestDataProvider.class)
    public void apiCreatedOrderAppearsInOrderHistory(Address address, PaymentDetails payment) {
        Response createdOrder = new OrderApi().createOrder(new Order(null, 1, List.of(new CartItem(1, 1)), "CREATED"));
        String orderId = String.valueOf(createdOrder.jsonPath().getInt("id"));
        ((org.openqa.selenium.JavascriptExecutor) driver())
                .executeScript("window.localStorage.setItem('apiOrderId', arguments[0]);", orderId);

        OrderHistoryPage orderHistoryPage = new LoginPage(driver())
                .open()
                .loginAs(config.get("default.email"), config.get("default.password"))
                .openOrderHistory();

        Assertions.assertThat(orderHistoryPage.containsOrder(orderId))
                .as("Order created through API should be visible in UI order history")
                .isTrue();
    }
}
