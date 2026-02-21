package tests.customer;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomerAccountPage;
import pages.CustomerLoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Customer Portal")
@Feature("Authentication")
public class CustomerAuthTest extends BaseTest {

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

        assertTrue(accountPage.getWelcomeName().contains(userName),
                "Expected welcome message to contain user: " + userName);

        CustomerLoginPage logoutPage = accountPage.clickLogout();
        assertTrue(logoutPage.isUserDropdownVisible(), "Expected user dropdown to be visible after logging out");
    }
}
