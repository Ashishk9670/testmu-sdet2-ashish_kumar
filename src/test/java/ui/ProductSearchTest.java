package ui;

import base.BaseTest;
import dataproviders.TestDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.Product;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.SearchResultsPage;

@Epic("E-commerce UI")
@Feature("Product Search")
public class ProductSearchTest extends BaseTest {

    @Test(groups = {"ui", "smoke"}, dataProvider = "products", dataProviderClass = TestDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    public void searchProductAndAddToCart(Product product) {
        HomePage homePage = new HomePage(driver());
        SearchResultsPage resultsPage = homePage.searchFor(product.searchTerm());

        Assertions.assertThat(resultsPage.hasResults()).isTrue();
        resultsPage.addFirstProductToCart();
        Assertions.assertThat(resultsPage.getSuccessNotification()).containsIgnoringCase("shopping cart");

        CartPage cartPage = homePage.openCart();
        Assertions.assertThat(cartPage.hasProduct()).isTrue();
    }
}
