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

@Feature("Swag Labs Login")
public class LoginTest extends BaseTest {
    private JsonNode loginData;

    @BeforeEach
    public void setupData() {
        loginData = JsonDataUtils.getSwagLabsData().get("loginData");
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify valid user can login successfully")
    public void testValidLogin() {
        JsonNode user = loginData.get("validUser");
        loginPage.login(user.get("username").asText(), user.get("password").asText());
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should be on the inventory page after login");
    }

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideParameterizedLogin")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify various invalid login scenarios show correct error messages")
    public void testInvalidLoginScenarios(String username, String password, String expectedError) {
        loginPage.login(username, password);
        Assertions.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                "Expected error message: " + expectedError + " but got: " + loginPage.getErrorMessage());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify locked out user receives correct error message")
    public void testLockedOutUserLogin() {
        JsonNode user = loginData.get("lockedOutUser");
        loginPage.login(user.get("username").asText(), user.get("password").asText());
        Assertions.assertTrue(loginPage.getErrorMessage().contains(user.get("expectedError").asText()));
    }
}
