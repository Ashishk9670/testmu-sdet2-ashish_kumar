package api.specs;

import config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {
    private RequestSpecFactory() {
    }

    public static RequestSpecification defaultRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getInstance().get("base.api.url"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .addHeader("Accept-Language", "en-US,en;q=0.9")
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification authenticatedRequestSpec(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(defaultRequestSpec())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
