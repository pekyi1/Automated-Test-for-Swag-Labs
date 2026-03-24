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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.JsonDataUtils;

@Feature("Swag Labs Checkout")
public class CheckoutTest extends BaseTest {
    private JsonNode checkoutData;
    private JsonNode loginData;

    @BeforeEach
    public void setupData() {
        JsonNode root = JsonDataUtils.getSwagLabsData();
        checkoutData = root.get("checkoutData");
        loginData = root.get("loginData");
    }

    public void proceedToCheckout() {
        JsonNode user = loginData.get("validUser");
        loginPage.login(user.get("username").asText(), user.get("password").asText());
        inventoryPage.addBackpackToCart();
        inventoryPage.openCart();
        cartPage.checkout();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify filling out checkout step one correctly navigates to step two")
    public void testValidCheckoutStepOne() {
        proceedToCheckout();
        JsonNode customer = checkoutData.get("validCustomer");
        checkoutStepOnePage.enterInformation(
                customer.get("firstName").asText(),
                customer.get("lastName").asText(),
                customer.get("zipCode").asText());
        Assertions.assertTrue(checkoutStepTwoPage.isOnCheckoutStepTwoPage(),
                "User should proceed to checkout step two.");
    }

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideParameterizedCheckout")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify checkout validation errors for missing or invalid information")
    public void testCheckoutInformationValidation(String firstName, String lastName, String zipCode,
            String expectedError) {
        proceedToCheckout();
        checkoutStepOnePage.enterInformation(firstName, lastName, zipCode);
        Assertions.assertTrue(checkoutStepOnePage.getErrorMessage().contains(expectedError),
                "Expected error: " + expectedError + " but got: " + checkoutStepOnePage.getErrorMessage());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cancelling on checkout step one navigates back to cart")
    public void testCancelCheckoutStepOne() {
        proceedToCheckout();
        checkoutStepOnePage.cancelCheckout();
        Assertions.assertTrue(cartPage.isOnCartPage(), "User should be redirected back to the cart page.");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify summary subtotal on checkout step two matches the item values")
    public void testCheckoutStepTwoSummary() {
        proceedToCheckout();
        JsonNode customer = checkoutData.get("validCustomer");
        checkoutStepOnePage.enterInformation(
                customer.get("firstName").asText(),
                customer.get("lastName").asText(),
                customer.get("zipCode").asText());
        Assertions.assertTrue(checkoutStepTwoPage.getSubtotal().contains("29.99"),
                "Subtotal should reflect backpack price of 29.99.");
        Assertions.assertEquals(1, checkoutStepTwoPage.getSummaryItemsCount(), "Should display 1 summary item.");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify completing the purchase redirects to checkout complete page with thank you message")
    public void testFinishCheckoutFlow() {
        proceedToCheckout();
        JsonNode customer = checkoutData.get("anotherValidCustomer");
        checkoutStepOnePage.enterInformation(
                customer.get("firstName").asText(),
                customer.get("lastName").asText(),
                customer.get("zipCode").asText());
        checkoutStepTwoPage.finishCheckout();
        Assertions.assertTrue(checkoutCompletePage.isOnCheckoutCompletePage(),
                "User should arrive at checkout completion.");
        Assertions.assertEquals("Thank you for your order!", checkoutCompletePage.getThankYouMessage(),
                "Order success message should be displayed.");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify Back to Products button goes safely to inventory")
    public void testBackToProductsNavigation() {
        proceedToCheckout();
        JsonNode customer = checkoutData.get("anotherValidCustomer");
        checkoutStepOnePage.enterInformation(
                customer.get("firstName").asText(),
                customer.get("lastName").asText(),
                customer.get("zipCode").asText());
        checkoutStepTwoPage.finishCheckout();
        checkoutCompletePage.goBackToProducts();
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "Should be redirected back to inventory.");
    }
}
