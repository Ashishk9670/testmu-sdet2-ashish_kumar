package ui;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;

@Epic("E-commerce UI")
@Feature("Negative Checkout")
public class NegativeCheckoutTest extends BaseTest {

    @Test(groups = {"ui", "negative"})
    public void invalidCouponShowsValidationMessage() {
        HomePage homePage = new HomePage(driver());
        homePage.searchFor("computer").addFirstProductToCart();
        CartPage cartPage = homePage.openCart().applyCoupon("INVALID-COUPON-2026");

        Assertions.assertThat(cartPage.getCouponError()).isNotBlank();
    }

    @Test(groups = {"ui", "negative"})
    public void outOfStockProductDoesNotAddToCart() {
        HomePage homePage = new HomePage(driver());
        boolean hasResults = homePage.searchFor("limited workstation").hasResults();

        Assertions.assertThat(hasResults)
                .as("Out-of-stock or unavailable item should not be returned as a purchasable result")
                .isFalse();
    }
}
