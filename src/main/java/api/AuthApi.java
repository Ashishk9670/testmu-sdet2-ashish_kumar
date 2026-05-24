package api;

import api.specs.ResponseSpecFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.AuthRequest;
import models.AuthResponse;

public class AuthApi {
    private final APIClient client = new APIClient();

    @Step("Login through API")
    public AuthResponse login(AuthRequest request) {
        return loginRaw(request)
                .then()
                .spec(ResponseSpecFactory.statusAnyOf(200, 201))
                .extract()
                .as(AuthResponse.class);
    }

    public Response loginRaw(AuthRequest request) {
        return client.post("/auth/login", request);
    }
}
