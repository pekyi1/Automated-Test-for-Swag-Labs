package tests.manager;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomersPage;
import pages.ManagerPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Bank Manager Portal")
@Feature("Customer Management")
public class ManagerCustomersTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideSearchCustomers")
    @Tag("regression")
    @Story("Search Customers")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test navigates to the Manager portal, goes to the Customers list, and verifies that a specific customer can be found via search.")
    public void testSearchAndVerifyCustomer(String customerName) {
        ManagerPage managerPage = loginPage.clickBankManagerLogin();
        CustomersPage customersPage = managerPage.clickCustomersTab();

        customersPage.searchCustomer(customerName);

        assertTrue(customersPage.isCustomerInList(customerName),
                "Expected customer " + customerName + " to be found in the customers list after searching");
    }
}
