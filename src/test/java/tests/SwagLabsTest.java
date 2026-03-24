package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.JsonDataUtils;

@Feature("Swag Labs Shopping Flow")
public class SwagLabsTest extends BaseTest {
    private JsonNode testData;

    @BeforeEach
    public void setupData() {
        testData = JsonDataUtils.getSwagLabsData();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login, add items to cart, and complete checkout")
    public void testLoginCartCheckoutFlow() {
        JsonNode login = testData.get("loginData").get("validUser");
        loginPage.login(login.get("username").asText(), login.get("password").asText());
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should be on the inventory page after login");

        inventoryPage.addBackpackToCart();
        inventoryPage.addBikeLightToCart();
        inventoryPage.openCart();

        cartPage.checkout();

        JsonNode customer = testData.get("checkoutData").get("validCustomer");
        checkoutStepOnePage.enterInformation(
                customer.get("firstName").asText(),
                customer.get("lastName").asText(),
                customer.get("zipCode").asText());
        checkoutStepTwoPage.finishCheckout();

        Assertions.assertEquals("Thank you for your order!", checkoutCompletePage.getThankYouMessage(),
                "Order completion message mismatch");
    }
}
