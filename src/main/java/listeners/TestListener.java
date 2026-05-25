package listeners;

import driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureUtils;
import utils.ScreenshotUtils;

import config.ConfigReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TestListener implements ITestListener, IExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onExecutionStart() {
        ConfigReader config = ConfigReader.getInstance();
        try {
            Files.createDirectories(Path.of("allure-results"));
            String content = """
                    Environment=%s
                    Browser=%s
                    Headless=%s
                    UI URL=%s
                    API URL=%s
                    """.formatted(
                    config.get("env", "qa"),
                    System.getProperty("browser", config.get("browser", "chrome")),
                    System.getProperty("headless", config.get("headless", "true")),
                    config.get("base.ui.url"),
                    config.get("base.api.url")
            );
            Files.writeString(Path.of("allure-results", "environment.properties"), content);
        } catch (IOException e) {
            LOGGER.warn("Unable to write Allure environment metadata", e);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test failed: {}", result.getName(), result.getThrowable());
        Allure.addAttachment("Failure reason", String.valueOf(result.getThrowable()));
        attachRecentLogs();
        try {
            ScreenshotUtils.capture(result.getName());
            AllureUtils.attachPng("Failure screenshot", ScreenshotUtils.captureBytes());
        } catch (IllegalStateException | WebDriverException e) {
            LOGGER.warn("Screenshot capture skipped: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test passed: {}", result.getName());
    }

    private void attachRecentLogs() {
        Path logPath = Path.of("logs", "automation.log");
        if (!Files.exists(logPath)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(logPath, StandardCharsets.UTF_8);
            int start = Math.max(0, lines.size() - 200);
            String recentLogs = String.join(System.lineSeparator(), lines.subList(start, lines.size()));
            AllureUtils.attachText("Recent logs", recentLogs);
        } catch (IOException e) {
            LOGGER.warn("Unable to attach recent logs", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Test skipped: {}", result.getName());
        try {
            DriverManager.getDriver();
        } catch (IllegalStateException ignored) {
            return;
        }
    }
}
