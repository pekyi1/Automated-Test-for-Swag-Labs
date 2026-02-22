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
@Feature("Authentication")
public class CustomerAuthTest extends BaseTest {

        @Test
        @Tag("regression")
        @Story("UI State Validation")
        @Severity(SeverityLevel.NORMAL)
        @Description("This test verifies that the login button is hidden until a customer name is selected.")
        public void testLoginButtonIsHiddenInitially() {
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                assertFalse(customerLoginPage.isLoginButtonVisible(),
                                "Expected the Login button to be hidden before selecting a user");
        }

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerNoAccount")
        @Tag("regression")
        @Story("UI State Validation")
        @Severity(SeverityLevel.NORMAL)
        @Description("This test verifies that a customer without any accounts sees the 'Please open an account' message.")
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
        @Story("Customer Login and Logout Flow")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test verifies that a customer can successfully log into their account and log out successfully.")
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
