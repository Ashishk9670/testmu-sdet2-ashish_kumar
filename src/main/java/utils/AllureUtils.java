package utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class AllureUtils {
    private AllureUtils() {
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content, ".txt");
    }

    public static void attachJson(String name, String content) {
        Allure.addAttachment(name, "application/json", content, ".json");
    }

    public static void attachPng(String name, byte[] bytes) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(bytes), ".png");
    }

    public static void attachUtf8(String name, String content) {
        Allure.addAttachment(name, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
    }
}
