package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Feature("Swag Labs Cart")
public class CartTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cart contains items added from inventory")
    public void testCartContainsAddedItems() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.openCart();
        Assertions.assertTrue(cartPage.isOnCartPage(), "Should be on Cart Page.");
        Assertions.assertEquals(1, cartPage.getCartItemsCount(), "Cart should contain 1 item.");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify removing an item from the cart removes it completely")
    public void testRemoveItemFromCart() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.openCart();
        cartPage.removeBackpack();
        Assertions.assertEquals(0, cartPage.getCartItemsCount(), "Cart should contain 0 items after removal.");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify 'Continue Shopping' navigates back to inventory page")
    public void testContinueShoppingNavigation() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.openCart();
        cartPage.continueShopping();
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should be returned to inventory page.");
    }
}
