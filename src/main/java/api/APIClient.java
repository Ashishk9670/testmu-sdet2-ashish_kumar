package api;

import api.specs.RequestSpecFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class APIClient {
    private final RequestSpecification requestSpecification;

    public APIClient() {
        this(RequestSpecFactory.defaultRequestSpec());
    }

    public APIClient(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    @Step("GET {path}")
    public Response get(String path) {
        return given().spec(requestSpecification).when().get(path);
    }

    @Step("POST {path}")
    public Response post(String path, Object body) {
        return given().spec(requestSpecification).body(body).when().post(path);
    }

    @Step("PUT {path}")
    public Response put(String path, Object body) {
        return given().spec(requestSpecification).body(body).when().put(path);
    }

    @Step("DELETE {path}")
    public Response delete(String path) {
        return given().spec(requestSpecification).when().delete(path);
    }
}
