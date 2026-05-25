package api;

import api.specs.RequestSpecFactory;
import config.ConfigReader;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static io.restassured.RestAssured.given;

public class APIClient {
    private final RequestSpecification requestSpecification;
    private final int apiRetryCount;
    private final long apiRetryDelayMs;

    public APIClient() {
        this(RequestSpecFactory.defaultRequestSpec());
    }

    public APIClient(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
        this.apiRetryCount = ConfigReader.getInstance().getInt("api.retry.count", 2);
        this.apiRetryDelayMs = ConfigReader.getInstance().getInt("api.retry.delay.ms", 1000);
    }

    @Step("GET {path}")
    public Response get(String path) {
        return executeWithRetry(() -> given().spec(requestSpecification).when().get(path));
    }

    @Step("POST {path}")
    public Response post(String path, Object body) {
        return executeWithRetry(() -> given().spec(requestSpecification).body(body).when().post(path));
    }

    @Step("PUT {path}")
    public Response put(String path, Object body) {
        return executeWithRetry(() -> given().spec(requestSpecification).body(body).when().put(path));
    }

    @Step("DELETE {path}")
    public Response delete(String path) {
        return executeWithRetry(() -> given().spec(requestSpecification).when().delete(path));
    }

    private Response executeWithRetry(Supplier<Response> request) {
        Response response = null;
        for (int attempt = 1; attempt <= apiRetryCount + 1; attempt++) {
            try {
                response = request.get();
                if (!shouldRetry(response.statusCode()) || attempt > apiRetryCount) {
                    return response;
                }
            } catch (RuntimeException e) {
                if (!shouldRetryException(e) || attempt > apiRetryCount) {
                    throw e;
                }
            }
            sleepBeforeRetry(attempt);
        }
        return response;
    }

    private boolean shouldRetry(int statusCode) {
        return statusCode == 403 || statusCode == 429 || statusCode >= 500;
    }

    private boolean shouldRetryException(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            if (current instanceof IOException
                    || current instanceof SocketException
                    || current instanceof SocketTimeoutException
                    || current instanceof ConnectException
                    || current instanceof UnknownHostException
                    || current instanceof SSLException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    private void sleepBeforeRetry(int attempt) {
        try {
            TimeUnit.MILLISECONDS.sleep(apiRetryDelayMs * attempt);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
