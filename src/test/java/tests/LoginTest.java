package tests;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Feature("Swag Labs Login")
public class LoginTest extends BaseTest {
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify valid user can login successfully")
    public void testValidLogin() {
        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should be on the inventory page after login");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify invalid login shows error message")
    public void testInvalidLogin() {
        loginPage.login("invalid_user", "wrong_password");
        Assertions.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match any user in this service"));
    }
}
