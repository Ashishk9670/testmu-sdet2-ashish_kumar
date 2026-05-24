package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private final By cartTable = By.cssSelector(".cart");
    private final By productName = By.cssSelector(".cart .product-name");
    private final By termsCheckbox = By.id("termsofservice");
    private final By checkoutButton = By.id("checkout");
    private final By couponInput = By.id("discountcouponcode");
    private final By applyCouponButton = By.id("applydiscountcouponcode");
    private final By couponError = By.cssSelector(".message-failure");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasProduct() {
        waitForElement(cartTable);
        return isDisplayed(productName);
    }

    public String getFirstProductName() {
        return getText(productName);
    }

    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        click(termsCheckbox);
        click(checkoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Apply coupon: {couponCode}")
    public CartPage applyCoupon(String couponCode) {
        type(couponInput, couponCode);
        click(applyCouponButton);
        return this;
    }

    public String getCouponError() {
        return getText(couponError);
    }
}
