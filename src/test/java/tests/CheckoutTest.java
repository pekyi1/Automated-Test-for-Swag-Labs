package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Feature("Swag Labs Checkout")
public class CheckoutTest extends BaseTest {

    public void proceedToCheckout() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.openCart();
        cartPage.checkout();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify filling out checkout step one correctly navigates to step two")
    public void testValidCheckoutStepOne() {
        proceedToCheckout();
        checkoutStepOnePage.enterInformation("Frank", "Ocean", "12345");
        Assertions.assertTrue(checkoutStepTwoPage.isOnCheckoutStepTwoPage(),
                "User should proceed to checkout step two.");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify omitting first name shows an error message")
    public void testEmptyFirstNameCheckout() {
        proceedToCheckout();
        checkoutStepOnePage.enterInformation("", "Ocean", "12345");
        Assertions.assertTrue(checkoutStepOnePage.getErrorMessage().contains("First Name is required"));
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
        checkoutStepOnePage.enterInformation("Frank", "Ocean", "12345");
        Assertions.assertTrue(checkoutStepTwoPage.getSubtotal().contains("29.99"),
                "Subtotal should reflect backpack price of 29.99.");
        Assertions.assertEquals(1, checkoutStepTwoPage.getSummaryItemsCount(), "Should display 1 summary item.");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify completing the purchase redirects to checkout complete page with thank you message")
    public void testFinishCheckoutFlow() {
        proceedToCheckout();
        checkoutStepOnePage.enterInformation("Jane", "Doe", "54321");
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
        checkoutStepOnePage.enterInformation("Jane", "Doe", "54321");
        checkoutStepTwoPage.finishCheckout();
        checkoutCompletePage.goBackToProducts();
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "Should be redirected back to inventory.");
    }
}
