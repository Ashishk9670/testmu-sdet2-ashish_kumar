package pages;

import base.BasePage;
import io.qameta.allure.Step;
import models.Address;
import models.PaymentDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private final By billingFirstName = By.id("BillingNewAddress_FirstName");
    private final By billingLastName = By.id("BillingNewAddress_LastName");
    private final By billingEmail = By.id("BillingNewAddress_Email");
    private final By billingCountry = By.id("BillingNewAddress_CountryId");
    private final By billingCity = By.id("BillingNewAddress_City");
    private final By billingAddress1 = By.id("BillingNewAddress_Address1");
    private final By billingZip = By.id("BillingNewAddress_ZipPostalCode");
    private final By billingPhone = By.id("BillingNewAddress_PhoneNumber");
    private final By billingContinue = By.cssSelector("button[name='save'].new-address-next-step-button, #billing-buttons-container button");
    private final By shippingContinue = By.cssSelector("#shipping-buttons-container button");
    private final By shippingMethodContinue = By.cssSelector(".shipping-method-next-step-button");
    private final By paymentMethodContinue = By.cssSelector(".payment-method-next-step-button");
    private final By paymentInfoContinue = By.cssSelector(".payment-info-next-step-button");
    private final By confirmButton = By.cssSelector(".confirm-order-next-step-button");
    private final By validationError = By.cssSelector(".field-validation-error, .validation-summary-errors");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill billing address")
    public CheckoutPage fillBillingAddress(Address address) {
        setIfPresent(billingFirstName, address.firstName());
        setIfPresent(billingLastName, address.lastName());
        setIfPresent(billingEmail, address.email());
        if (!driver.findElements(billingCountry).isEmpty()) {
            selectDropdownByVisibleText(billingCountry, address.country());
        }
        setIfPresent(billingCity, address.city());
        setIfPresent(billingAddress1, address.addressLine1());
        setIfPresent(billingZip, address.postalCode());
        setIfPresent(billingPhone, address.phone());
        click(billingContinue);
        return this;
    }

    @Step("Select shipping method")
    public CheckoutPage selectShippingMethod() {
        if (!driver.findElements(shippingContinue).isEmpty()) {
            click(shippingContinue);
        }
        click(shippingMethodContinue);
        return this;
    }

    @Step("Select payment method: {payment.method}")
    public CheckoutPage selectPaymentMethod(PaymentDetails payment) {
        click(paymentMethodContinue);
        click(paymentInfoContinue);
        return this;
    }

    @Step("Confirm order")
    public OrderConfirmationPage confirmOrder() {
        click(confirmButton);
        return new OrderConfirmationPage(driver);
    }

    @Step("Complete checkout")
    public OrderConfirmationPage completeCheckout(Address address, PaymentDetails payment) {
        fillBillingAddress(address);
        selectShippingMethod();
        selectPaymentMethod(payment);
        return confirmOrder();
    }

    public String getValidationError() {
        return getText(validationError);
    }

    private void setIfPresent(By locator, String value) {
        if (!driver.findElements(locator).isEmpty()) {
            type(locator, value);
        }
    }
}
