package api.specs;

import config.ConfigReader;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public final class ResponseSpecFactory {
    private ResponseSpecFactory() {
    }

    public static ResponseSpecification status(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(maxResponseTime()), TimeUnit.MILLISECONDS)
                .build();
    }

    public static ResponseSpecification noContentTypeStatus(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(maxResponseTime()), TimeUnit.MILLISECONDS)
                .build();
    }

    public static ResponseSpecification statusAnyOf(int... statusCodes) {
        org.hamcrest.Matcher<Integer>[] matchers = new org.hamcrest.Matcher[statusCodes.length];
        for (int i = 0; i < statusCodes.length; i++) {
            matchers[i] = is(statusCodes[i]);
        }
        return new ResponseSpecBuilder()
                .expectStatusCode(anyOf(matchers))
                .expectResponseTime(lessThan(maxResponseTime()), TimeUnit.MILLISECONDS)
                .build();
    }

    private static long maxResponseTime() {
        return ConfigReader.getInstance().getInt("api.response.time.ms", 10000);
    }
}
