package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderHistoryPage extends BasePage {
    private final By orderList = By.cssSelector(".order-list");

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean containsOrder(String orderId) {
        waitForElement(orderList);
        return driver.getPageSource().contains(orderId);
    }
}
