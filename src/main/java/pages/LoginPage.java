package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By loginLink = By.cssSelector("a.ico-login");
    private final By emailInput = By.id("Email");
    private final By passwordInput = By.id("Password");
    private final By loginButton = By.cssSelector("button.login-button");
    private final By validationSummary = By.cssSelector(".validation-summary-errors, .message-error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open login page")
    public LoginPage open() {
        click(loginLink);
        return this;
    }

    @Step("Login as {email}")
    public HomePage loginAs(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);
        return new HomePage(driver);
    }

    @Step("Submit empty login form")
    public LoginPage submitEmptyLogin() {
        click(loginButton);
        return this;
    }

    public String getValidationMessage() {
        return getText(validationSummary);
    }
}
