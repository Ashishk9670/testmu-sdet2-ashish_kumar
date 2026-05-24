package utils;

import driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {
    private ScreenshotUtils() {
    }

    public static Path capture(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            Files.createDirectories(Path.of("screenshots"));
            Path target = Path.of("screenshots", safeName(testName) + "-" + timestamp() + ".png");
            Files.write(target, screenshot);
            return target;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save screenshot", e);
        }
    }

    public static byte[] captureBytes() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    private static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
    }

    private static String safeName(String name) {
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
