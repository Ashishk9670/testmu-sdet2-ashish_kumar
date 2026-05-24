package utils;

import config.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {
    private WaitUtils() {
    }

    public static void waitForPageReady(WebDriver driver) {
        int timeout = ConfigReader.getInstance().getInt("timeout.seconds", 15);
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(pageLoaded());
    }

    private static ExpectedCondition<Boolean> pageLoaded() {
        return driver -> "complete".equals(((JavascriptExecutor) driver).executeScript("return document.readyState"));
    }
}
