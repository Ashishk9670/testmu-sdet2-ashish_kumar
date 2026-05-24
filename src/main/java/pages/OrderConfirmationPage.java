package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends BasePage {
    private final By successTitle = By.cssSelector(".section.order-completed .title, .order-completed .title, .page-title");
    private final By orderNumber = By.cssSelector(".order-number");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getSuccessMessage() {
        return getText(successTitle);
    }

    public String getOrderNumber() {
        return getText(orderNumber).replaceAll("[^0-9]", "");
    }
}
