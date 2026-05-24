package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By accountLink = By.cssSelector("a.ico-account");
    private final By logoutLink = By.cssSelector("a.ico-logout");
    private final By searchInput = By.id("small-searchterms");
    private final By searchButton = By.cssSelector("button.search-box-button");
    private final By cartLink = By.cssSelector("a.ico-cart");
    private final By orderList = By.cssSelector(".order-list");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoggedIn() {
        return isDisplayed(accountLink) || isDisplayed(logoutLink);
    }

    @Step("Search product: {term}")
    public SearchResultsPage searchFor(String term) {
        type(searchInput, term);
        click(searchButton);
        return new SearchResultsPage(driver);
    }

    @Step("Open cart")
    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    @Step("Open order history")
    public OrderHistoryPage openOrderHistory() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("location.hash = '#account';");
        waitForElement(orderList);
        return new OrderHistoryPage(driver);
    }

    @Step("Logout")
    public void logout() {
        if (isDisplayed(logoutLink)) {
            click(logoutLink);
        }
    }
}
