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
