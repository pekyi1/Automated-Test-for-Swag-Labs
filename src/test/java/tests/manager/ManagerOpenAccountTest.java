package tests.manager;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.ManagerPage;
import pages.OpenAccountPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
        ManagerPage managerPage = loginPage.clickBankManagerLogin();
        OpenAccountPage openAccountPage = managerPage.clickOpenAccountTab();

        openAccountPage.selectCustomer(customerName)
                .selectCurrency(currency)
                .clickProcess();

        String alertText = openAccountPage.getAlertTextAndAccept();
        assertTrue(alertText != null && alertText.contains("Account created successfully"),
                "Expected success alert after opening account. Alert text: " + alertText);
    }
}
