package base;

import config.ConfigReader;
import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseTest {
    protected final ConfigReader config = ConfigReader.getInstance();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(@Optional String browser, @Optional String headless) {
        String resolvedBrowser = firstNonBlank(browser, System.getProperty("browser"), config.get("browser", "chrome"));
        boolean resolvedHeadless = Boolean.parseBoolean(firstNonBlank(headless, System.getProperty("headless"), config.get("headless", "true")));
        WebDriver driver = DriverFactory.createDriver(resolvedBrowser, resolvedHeadless);
        DriverManager.setDriver(driver);
        driver.get(config.get("base.ui.url"));
        utils.WaitUtils.waitForPageReady(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank() && !value.startsWith("${")) {
                return value;
            }
        }
        throw new IllegalArgumentException("No non-blank value found");
    }
}
