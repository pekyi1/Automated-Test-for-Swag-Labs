package tests;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.customer.CustomerLoginPage;
import pages.manager.ManagerPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Digital Banking Application")
@Feature("Routing")
public class XYZNavigationTest extends BaseTest {

    @Test
    @Tag("smoke")
    @Story("Customer Portal Navigation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("This test verifies that the Customer Login button correctly navigates to the Customer portal view.")
    public void verifyCustomerLoginNavigation() {
        // Step 1: Click Customer Login button
        CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();

        // Step 2: Verify user dropdown is visible
        assertAll("Customer login navigation checks",
                () -> assertTrue(customerLoginPage.isUserDropdownVisible(),
                        "User selection dropdown should be visible on Customer Login page"));
    }

    @Test
    @Tag("smoke")
    @Story("Manager Portal Navigation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("This test verifies that the Bank Manager Login button correctly navigates to the Manager portal view.")
    public void verifyBankManagerNavigation() {
        // Step 1: Click Bank Manager Login button
        ManagerPage managerPage = loginPage.clickBankManagerLogin();

        // Step 2: Verify Manager tabs are visible
        assertAll("Bank manager navigation checks",
                () -> assertTrue(managerPage.areManagerTabsVisible(),
                        "Add Customer, Open Account, and Customers tabs should be visible on Manager page"));
    }
}
