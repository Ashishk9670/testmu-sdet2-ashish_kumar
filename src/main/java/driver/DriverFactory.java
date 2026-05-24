package driver;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

public final class DriverFactory {
    private DriverFactory() {
    }

    static {
        // Prevent WebDriverManager from hanging on proxy/network issues
        // (e.g. Docker Desktop's kubernetes.docker.internal entry in hosts file)
        System.setProperty("java.net.useSystemProxies", "false");
    }

    public static WebDriver createDriver(String browserName, boolean headless) {
        BrowserType browser = BrowserType.from(browserName);
        WebDriver driver = switch (browser) {
            case CHROME -> createChrome(headless);
            case FIREFOX -> createFirefox(headless);
            case EDGE -> createEdge(headless);
        };
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                ConfigReader.getInstance().getInt("timeout.seconds", 15)));
        return driver;
    }

    private static WebDriver createChrome(boolean headless) {
        WebDriverManager.chromedriver().timeout(5).setup();
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking",
                "--remote-allow-origins=*",
                "--disable-blink-features=AutomationControlled",
                "--user-data-dir=" + tempProfile("chrome")
        );
        if (headless) {
            options.addArguments("--headless", "--window-size=1920,1080", "--disable-gpu", "--disable-dev-shm-usage");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {
        WebDriverManager.firefoxdriver().timeout(5).setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        if (headless) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdge(boolean headless) {
        WebDriverManager.edgedriver().timeout(5).setup();
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking",
                "--disable-blink-features=AutomationControlled",
                "--user-data-dir=" + tempProfile("edge")
        );
        if (headless) {
            options.addArguments("--headless", "--window-size=1920,1080", "--disable-gpu", "--disable-dev-shm-usage");
        }
        return new EdgeDriver(options);
    }

    private static String tempProfile(String browser) {
        return Path.of(System.getProperty("java.io.tmpdir"), "selenium-" + browser + "-" + UUID.randomUUID()).toString();
    }
}
