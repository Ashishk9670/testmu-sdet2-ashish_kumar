package ui;

import base.BaseTest;
import dataproviders.TestDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.User;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

@Epic("E-commerce UI")
@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Test(groups = {"ui", "smoke"}, dataProvider = "loginUsers", dataProviderClass = TestDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    public void loginValidation(User user, boolean shouldSucceed) {
        LoginPage loginPage = new LoginPage(driver()).open();
        HomePage homePage = loginPage.loginAs(user.email(), user.password());

        if (shouldSucceed) {
            Assertions.assertThat(homePage.isLoggedIn()).isTrue();
            homePage.logout();
        } else {
            Assertions.assertThat(loginPage.getValidationMessage()).isNotBlank();
        }
    }

    @Test(groups = {"ui", "negative"})
    @Severity(SeverityLevel.NORMAL)
    public void emptyCredentialsValidation() {
        LoginPage loginPage = new LoginPage(driver()).open().submitEmptyLogin();

        Assertions.assertThat(loginPage.getValidationMessage())
                .as("Empty credential validation should be visible")
                .isNotBlank();
    }
}
