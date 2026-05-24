package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

public final class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private JsonUtils() {
    }

    public static <T> T readResource(String resource, Class<T> type) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("JSON resource not found: " + resource);
            }
            return MAPPER.readValue(inputStream, type);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read JSON resource: " + resource, e);
        }
    }

    public static String toJson(Object object) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to serialize object", e);
        }
    }
}
