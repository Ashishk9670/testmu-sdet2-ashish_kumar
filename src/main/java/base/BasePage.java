package base;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = ConfigReader.getInstance().getInt("timeout.seconds", 15);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void scrollIntoView(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", waitForElement(locator));
    }

    protected void jsClick(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", waitForElement(locator));
    }

    protected void selectDropdownByVisibleText(By locator, String visibleText) {
        new Select(waitForElement(locator)).selectByVisibleText(visibleText);
    }

    protected String getText(By locator) {
        return waitForElement(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        return !driver.findElements(locator).isEmpty() && driver.findElement(locator).isDisplayed();
    }

    protected void waitForUrlContaining(String value) {
        wait.until(ExpectedConditions.urlContains(value));
    }
}
