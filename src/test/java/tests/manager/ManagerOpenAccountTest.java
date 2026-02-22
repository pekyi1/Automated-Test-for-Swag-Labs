package tests.manager;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.ManagerPage;
import pages.OpenAccountPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Bank Manager Portal")
@Feature("Account Management")
public class ManagerOpenAccountTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideOpenAccounts")
    @Tag("regression")
    @Story("Open New Account")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test navigates to the Manager portal, fills out the Open Account form for a customer, submits it, and verifies the success alert.")
    public void testOpenAccount(String customerName, String currency) {
        // Step 1: Login as Manager and navigate to Open Account tab
        ManagerPage managerPage = loginPage.clickBankManagerLogin();
        OpenAccountPage openAccountPage = managerPage.clickOpenAccountTab();

        // Step 2: Select customer and currency, then process
        openAccountPage.selectCustomer(customerName)
                .selectCurrency(currency)
                .clickProcess();

        // Step 3: Verify success alert
        String alertText = openAccountPage.getAlertTextAndAccept();
        assertAll("Open account checks",
                () -> assertTrue(
                        alertText != null && alertText.contains("Account created successfully with account Number :"),
                        "Expected success alert with account Number after opening account. Alert text: " + alertText));
    }

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideInvalidOpenAccounts")
    @Tag("regression")
    @Story("Open New Account Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies that the system prevents account creation if required fields are missing.")
    public void testOpenAccountValidation(String customerName, String currency) {
        // Step 1: Login as Manager and navigate to Open Account tab
        ManagerPage managerPage = loginPage.clickBankManagerLogin();
        OpenAccountPage openAccountPage = managerPage.clickOpenAccountTab();

        // Step 2: Leave required fields empty or partially filled and process
        if (!customerName.isEmpty()) {
            openAccountPage.selectCustomer(customerName);
        }
        if (!currency.isEmpty()) {
            openAccountPage.selectCurrency(currency);
        }
        openAccountPage.clickProcess();

        // Step 3: Verify account creation is prevented
        String alertText = openAccountPage.getAlertTextAndAccept();
        boolean isAlertPresentAndSuccess = alertText != null
                && alertText.contains("Account created successfully with account Number :");

        assertAll("Open invalid account checks",
                () -> assertFalse(isAlertPresentAndSuccess,
                        "Account should not have been created successfully without all required fields selected."));
    }
}
