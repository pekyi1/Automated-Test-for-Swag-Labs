package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Feature("Swag Labs Inventory")
public class InventoryTest extends BaseTest {
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify inventory page initially displays 6 items")
    public void testInventoryItemCount() {
        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertEquals(6, inventoryPage.getInventoryItemCount(), "Inventory should display exactly 6 items.");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify adding an item updates cart badge to 1")
    public void testAddToCartUpdatesBadge() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        Assertions.assertEquals("1", inventoryPage.getCartItemCount(), "Cart badge should display 1.");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify adding and then removing an item clears cart badge")
    public void testRemoveFromCartClearsBadge() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.removeBackpackFromCart();
        Assertions.assertFalse(inventoryPage.isCartBadgeDisplayed(), "Cart badge should disappear or display 0.");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify sorting by Z to A does not crash")
    public void testSortZToA() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.sortBy("za");
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should remain on inventory page after sorting.");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify logging out from sidebar menu")
    public void testLogoutViaSidebar() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.openMenu();
        inventoryPage.logout();
        Assertions.assertTrue(loginPage.isOnLoginPage(),
                "User should be successfully logged out and redirected to login page.");
    }
}
