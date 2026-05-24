package api;

import api.specs.ResponseSpecFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.AuthRequest;
import models.AuthResponse;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Epic("E-commerce API")
@Feature("Authentication API")
public class AuthApiTest {

    @Test(groups = {"api", "smoke"})
    public void loginReturnsToken() {
        AuthResponse response = new AuthApi().login(new AuthRequest("mor_2314", "83r5^_"));

        Assertions.assertThat(response.token()).isNotBlank();
    }

    @Test(groups = {"api", "negative"})
    public void invalidLoginReturnsUnauthorized() {
        new AuthApi().loginRaw(new AuthRequest("invalid", "invalid"))
                .then()
                .spec(ResponseSpecFactory.noContentTypeStatus(401));
    }
}
