package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {
    private final By resultsGrid = By.cssSelector(".product-grid");
    private final By productItems = By.cssSelector(".product-item");
    private final By firstAddToCartButton = By.cssSelector(".product-item button.button-2.product-box-add-to-cart-button");
    private final By barNotification = By.cssSelector(".bar-notification.success");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasResults() {
        waitForElement(resultsGrid);
        return !driver.findElements(productItems).isEmpty();
    }

    @Step("Add first available product to cart")
    public SearchResultsPage addFirstProductToCart() {
        scrollIntoView(firstAddToCartButton);
        click(firstAddToCartButton);
        waitForElement(barNotification);
        return this;
    }

    public String getSuccessNotification() {
        return getText(barNotification);
    }
}
