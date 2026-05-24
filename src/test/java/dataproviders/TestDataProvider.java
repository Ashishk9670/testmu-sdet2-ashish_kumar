package dataproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Address;
import models.PaymentDetails;
import models.Product;
import models.User;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;

public final class TestDataProvider {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TestDataProvider() {
    }

    @DataProvider(name = "loginUsers")
    public static Object[][] loginUsers() {
        JsonNode users = readTree("testdata/users.json");
        return new Object[][]{
                {toUser(users.get("validUser")), true},
                {toUser(users.get("invalidUser")), false}
        };
    }

    @DataProvider(name = "products")
    public static Object[][] products() {
        JsonNode products = readTree("testdata/products.json");
        return new Object[][]{{toProduct(products.get("computer"))}};
    }

    @DataProvider(name = "checkoutData")
    public static Object[][] checkoutData() {
        JsonNode checkout = readTree("testdata/checkout.json");
        Address address = Address.builder()
                .firstName(checkout.get("defaultAddress").get("firstName").asText())
                .lastName(checkout.get("defaultAddress").get("lastName").asText())
                .email(checkout.get("defaultAddress").get("email").asText())
                .country(checkout.get("defaultAddress").get("country").asText())
                .city(checkout.get("defaultAddress").get("city").asText())
                .addressLine1(checkout.get("defaultAddress").get("addressLine1").asText())
                .postalCode(checkout.get("defaultAddress").get("postalCode").asText())
                .phone(checkout.get("defaultAddress").get("phone").asText())
                .build();
        PaymentDetails payment = MAPPER.convertValue(checkout.get("payment"), PaymentDetails.class);
        return new Object[][]{{address, payment}};
    }

    private static User toUser(JsonNode node) {
        return MAPPER.convertValue(node, User.class);
    }

    private static Product toProduct(JsonNode node) {
        return MAPPER.convertValue(node, Product.class);
    }

    private static JsonNode readTree(String resource) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            return MAPPER.readTree(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read resource: " + resource, e);
        }
    }
}
