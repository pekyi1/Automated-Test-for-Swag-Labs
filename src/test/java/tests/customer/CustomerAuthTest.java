package tests.customer;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomerAccountPage;
import pages.CustomerLoginPage;
import pages.ManagerPage;
import pages.AddCustomerPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.Test;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Customer Portal")
@Feature("User Story 2: Manage Finances")
public class CustomerAuthTest extends BaseTest {

        @Test
        @Tag("regression")
        @Story("4. Transaction Security")
        @Severity(SeverityLevel.NORMAL)
        @Description("Customers should not be able to reset or alter their transaction history (or access accounts until selected).")
        public void testLoginButtonIsHiddenInitially() {
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                assertFalse(customerLoginPage.isLoginButtonVisible(),
                                "Expected the Login button to be hidden before selecting a user");
        }

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerNoAccount")
        @Tag("regression")
        @Story("4. Transaction Security")
        @Severity(SeverityLevel.NORMAL)
        @Description("Customers should not be able to access their accounts until an account has been created by a bank manager.")
        public void testCustomerNoAccountMessage(String firstName, String lastName, String postCode) {
                // Step 1: Login as Manager and add the customer
                ManagerPage managerPage = loginPage.clickBankManagerLogin();
                AddCustomerPage addCustomerPage = managerPage.clickAddCustomerTab();
                addCustomerPage.enterFirstName(firstName)
                                .enterLastName(lastName)
                                .enterPostCode(postCode)
                                .clickAddCustomerSubmit();
                addCustomerPage.getAlertTextAndAccept();

                // Step 2: Go back to home page
                loginPage = managerPage.clickHomeBtn();

                // Step 3: Login as the new customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                String userName = firstName + " " + lastName;
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                assertTrue(accountPage.getNoAccountMessage().contains("Please open an account with us."),
                                "Expected to see the 'Please open an account with us' message for user " + userName);
        }

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerAuth")
        @Tag("regression")
        @Story("4. Transaction Security")
        @Severity(SeverityLevel.CRITICAL)
        @Description("Customers should be authenticated before they can view transactions, deposit funds, and withdraw money.")
        public void testCustomerLoginAndLogout(String userName) {
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();

                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                CustomerLoginPage logoutPage = accountPage.clickLogout();

                assertAll("Customer login and logout checks",
                                () -> assertTrue(accountPage.getWelcomeName().contains(userName),
                                                "Expected welcome message to contain user: " + userName),
                                () -> assertTrue(logoutPage.isUserDropdownVisible(),
                                                "Expected user dropdown to be visible after logging out"));
        }
}
