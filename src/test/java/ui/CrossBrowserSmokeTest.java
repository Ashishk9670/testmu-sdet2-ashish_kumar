package ui;

import config.ConfigReader;
import driver.DriverFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;

@Epic("E-commerce UI")
@Feature("Cross Browser Smoke")
public class CrossBrowserSmokeTest {

    @DataProvider(name = "browsers")
    public Object[][] browsers() {
        String[] browsers = ConfigReader.getInstance().get("cross.browser.browsers", "chrome").split(",");
        Object[][] data = new Object[browsers.length][1];
        for (int i = 0; i < browsers.length; i++) {
            data[i][0] = browsers[i].trim();
        }
        return data;
    }

    @Test(groups = {"ui", "smoke"}, dataProvider = "browsers")
    public void homePageSearchWorksAcrossBrowsers(String browser) {
        ConfigReader config = ConfigReader.getInstance();
        WebDriver driver;
        try {
            driver = DriverFactory.createDriver(browser, true);
        } catch (SessionNotCreatedException e) {
            throw new SkipException("Browser is not available on this machine: " + browser, e);
        } catch (WebDriverException e) {
            throw new SkipException("Unable to start browser: " + browser, e);
        } catch (RuntimeException e) {
            throw new SkipException("Browser driver could not be prepared: " + browser, e);
        }
        try {
            driver.get(config.get("base.ui.url"));
            Assertions.assertThat(new HomePage(driver).searchFor("computer").hasResults()).isTrue();
        } finally {
            driver.quit();
        }
    }
}
