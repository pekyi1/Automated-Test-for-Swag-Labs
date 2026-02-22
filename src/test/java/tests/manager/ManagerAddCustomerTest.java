package tests.manager;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.AddCustomerPage;
import pages.ManagerPage;

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
@Feature("Customer Management")
public class ManagerAddCustomerTest extends BaseTest {

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideNewCustomers")
        @Tag("regression")
        @Story("Add New Customer")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test navigates to the Manager portal, fills out the Add Customer form, submits it, and verifies the success alert.")
        public void testAddCustomer(String firstName, String lastName, String postCode) {
                // Step 1: Login as Manager and navigate to Add Customer tab
                ManagerPage managerPage = loginPage.clickBankManagerLogin();
                AddCustomerPage addCustomerPage = managerPage.clickAddCustomerTab();

                // Step 2: Fill out customer details and submit
                addCustomerPage.enterFirstName(firstName)
                                .enterLastName(lastName)
                                .enterPostCode(postCode)
                                .clickAddCustomerSubmit();

                // Step 3: Verify success alert
                String alertText = addCustomerPage.getAlertTextAndAccept();
                assertAll("Add customer checks",
                                () -> assertTrue(
                                                alertText != null && alertText.contains(
                                                                "Customer added successfully with customer id :"),
                                                "Expected success alert with customer ID after adding customer. Alert text: "
                                                                + alertText));
        }

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideInvalidCustomers")
        @Tag("regression")
        @Story("Add New Customer Validation")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test verifies that the system prevents customer creation with invalid data (e.g., numbers in names, special chars).")
        public void testAddCustomerValidation(String firstName, String lastName, String postCode) {
                // Step 1: Login as Manager and navigate to Add Customer tab
                ManagerPage managerPage = loginPage.clickBankManagerLogin();
                AddCustomerPage addCustomerPage = managerPage.clickAddCustomerTab();

                // Step 2: Fill out invalid customer details and submit
                addCustomerPage.enterFirstName(firstName)
                                .enterLastName(lastName)
                                .enterPostCode(postCode)
                                .clickAddCustomerSubmit();

                // Step 3: Verify customer creation is prevented
                String alertText = addCustomerPage.getAlertTextAndAccept();
                boolean isAlertPresentAndSuccess = alertText != null
                                && alertText.contains("Customer added successfully with customer id :");

                // Some systems show HTML5 validation, meaning no alert appears at all
                // If an alert does appear, it should NOT say customer added successfully if
                // validation works
                assertAll("Add invalid customer checks",
                                () -> assertFalse(isAlertPresentAndSuccess,
                                                "Customer should not have been created successfully with invalid data: "
                                                                + firstName
                                                                + ", " + lastName
                                                                + ", " + postCode));
        }
}
