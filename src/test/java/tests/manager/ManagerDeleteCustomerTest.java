package tests.manager;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.manager.AddCustomerPage;
import pages.customer.CustomerLoginPage;
import pages.manager.CustomersPage;
import pages.manager.ManagerPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Digital Banking Application")
@Feature("Bank Manager \u2014 Customer & Account Administration")
public class ManagerDeleteCustomerTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideNewCustomers")
    @Tag("regression")
    @Story("US1: Delete accounts")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Bank managers should be able to delete customer accounts. When an account is deleted, the associated customer should no longer be able to access the account.")
    public void testDeleteCustomer(String firstName, String lastName, String postCode) {

        String fullName = firstName + " " + lastName;

        // Step 1: Login as Manager and add the customer
        ManagerPage managerPage = loginPage.clickBankManagerLogin();
        AddCustomerPage addCustomerPage = managerPage.clickAddCustomerTab();

        addCustomerPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostCode(postCode)
                .clickAddCustomerSubmit();

        // Accept the 'Customer added successfully' alert
        addCustomerPage.getAlertTextAndAccept();

        // Step 2: Navigate to Customers tab
        CustomersPage customersPage = managerPage.clickCustomersTab();

        // Verify they are in the table first
        customersPage.searchCustomer(firstName);
        assertTrue(customersPage.isCustomerInList(firstName), "Expected customer to be in the list before deleting.");

        // Step 3: Delete the customer
        customersPage.deleteCustomer(firstName);

        // Clear search and optionally wait a beat for angular to refresh the DOM
        customersPage.searchCustomer("");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Step 4: Verify the customer is no longer in the list
        customersPage.searchCustomer(firstName);
        assertFalse(customersPage.isCustomerInList(firstName),
                "Expected customer to be removed from the list after deletion.");

        // Step 5: Navigate back to the main login page to ensure customer cannot log in
        loginPage = managerPage.clickHomeBtn();

        // Navigate to Customer Login
        CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();

        // Step 6: Verify the customer's name is not available in the dropdown
        assertAll("Delete customer login checks",
                () -> assertFalse(customerLoginPage.isUserInDropdown(fullName),
                        "Deleted customer should not appear in the Customer Login dropdown."));
    }
}
