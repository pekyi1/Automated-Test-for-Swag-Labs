package tests.manager;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomersPage;
import pages.ManagerPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Digital Banking Application")
@Feature("Bank Manager \u2014 Customer & Account Administration")
public class ManagerCustomersTest extends BaseTest {

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideSearchCustomers")
        @Tag("regression")
        @Story("US1: Add customers")
        @Severity(SeverityLevel.NORMAL)
        @Description("The system should allow bank managers to add new customers and view them in the Customer List.")
        public void testSearchAndVerifyCustomer(String firstName, String lastName) {
                // Step 1: Login as Manager and navigate to Customers tab
                ManagerPage managerPage = loginPage.clickBankManagerLogin();
                CustomersPage customersPage = managerPage.clickCustomersTab();

                // Step 2: Search for the customer using first name
                customersPage.searchCustomer(firstName);

                // Step 3: Verify the customer appears in the list
                assertAll("Search customer checks (First Name)",
                                () -> assertTrue(customersPage.isCustomerInList(firstName),
                                                "Expected customer " + firstName
                                                                + " to be found in the customers list after searching by first name"));

                // Step 4: Search for the customer using last name
                customersPage.searchCustomer(lastName);

                // Step 5: Verify the customer appears in the list
                assertAll("Search customer checks (Last Name)",
                                () -> assertTrue(customersPage.isCustomerInList(lastName),
                                                "Expected customer " + lastName
                                                                + " to be found in the customers list after searching by last name"));
        }
}
