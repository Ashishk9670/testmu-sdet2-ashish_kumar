package ui;

import base.BaseTest;
import dataproviders.TestDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.Address;
import models.PaymentDetails;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.OrderConfirmationPage;

@Epic("E-commerce UI")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    @Test(groups = {"ui", "regression"}, dataProvider = "checkoutData", dataProviderClass = TestDataProvider.class)
    @Severity(SeverityLevel.BLOCKER)
    public void completeCheckoutFlow(Address address, PaymentDetails payment) {
        HomePage homePage = new HomePage(driver());
        homePage.searchFor("computer").addFirstProductToCart();

        CartPage cartPage = homePage.openCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        OrderConfirmationPage confirmationPage = checkoutPage.completeCheckout(address, payment);

        Assertions.assertThat(confirmationPage.getSuccessMessage()).containsIgnoringCase("order");
    }
}
