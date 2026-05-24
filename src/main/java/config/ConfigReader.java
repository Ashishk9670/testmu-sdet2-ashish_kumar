package config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

public final class ConfigReader {
    private static final ConfigReader INSTANCE = new ConfigReader();
    private final Properties properties = new Properties();

    private ConfigReader() {
        load("config/environment.properties");
        String env = get("env", "qa").toLowerCase(Locale.ROOT);
        load("config/%s.properties".formatted(env));
        overrideFromSystem("env");
        overrideFromSystem("browser");
        overrideFromSystem("headless");
        overrideFromSystem("base.ui.url");
        overrideFromSystem("base.api.url");
        overrideFromSystem("cross.browser.browsers");
    }

    public static ConfigReader getInstance() {
        return INSTANCE;
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing required configuration: " + key);
        }
        return resolveValue(key, value.trim());
    }

    public String get(String key, String defaultValue) {
        return resolveValue(key, properties.getProperty(key, defaultValue).trim());
    }

    public int getInt(String key, int defaultValue) {
        return Integer.parseInt(get(key, String.valueOf(defaultValue)));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    private void load(String resource) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IllegalStateException("Config resource not found: " + resource);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config resource: " + resource, e);
        }
    }

    private void overrideFromSystem(String key) {
        String value = System.getProperty(key);
        if (Objects.nonNull(value) && !value.isBlank()) {
            properties.setProperty(key, value);
        }
    }

    private String resolveValue(String key, String value) {
        if (!"base.ui.url".equals(key) || value.startsWith("http") || value.startsWith("file:")) {
            return value;
        }
        URL resource = Thread.currentThread().getContextClassLoader().getResource(value);
        if (resource == null) {
            return value;
        }
        try {
            return resource.toURI().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid UI resource URI: " + value, e);
        }
    }
}
