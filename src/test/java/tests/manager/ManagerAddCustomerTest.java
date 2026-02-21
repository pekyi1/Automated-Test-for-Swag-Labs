package tests.manager;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.AddCustomerPage;
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
public class ManagerAddCustomerTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideNewCustomers")
    @Tag("regression")
    @Story("Add New Customer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test navigates to the Manager portal, fills out the Add Customer form, submits it, and verifies the success alert.")
    public void testAddCustomer(String firstName, String lastName, String postCode) {
        ManagerPage managerPage = loginPage.clickBankManagerLogin();
        AddCustomerPage addCustomerPage = managerPage.clickAddCustomerTab();

        addCustomerPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostCode(postCode)
                .clickAddCustomerSubmit();

        String alertText = addCustomerPage.getAlertTextAndAccept();
        assertTrue(alertText != null && alertText.contains("Customer added successfully"),
                "Expected success alert after adding customer. Alert text: " + alertText);
    }
}
