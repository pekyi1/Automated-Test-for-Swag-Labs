package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Feature("Swag Labs Shopping Flow")
public class SwagLabsTest extends BaseTest {
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login, add items to cart, and complete checkout")
    public void testLoginCartCheckoutFlow() {
        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should be on the inventory page after login");

        inventoryPage.addBackpackToCart();
        inventoryPage.addBikeLightToCart();
        inventoryPage.openCart();

        cartPage.checkout();
        checkoutStepOnePage.enterInformation("John", "Doe", "12345");
        checkoutStepTwoPage.finishCheckout();

        Assertions.assertEquals("Thank you for your order!", checkoutCompletePage.getThankYouMessage(),
                "Order completion message mismatch");
    }
}
