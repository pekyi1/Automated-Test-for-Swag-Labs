package tests.customer;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomerAccountPage;
import pages.CustomerDepositPage;
import pages.CustomerLoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Customer Portal")
@Feature("Transactions")
public class CustomerDepositTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("utils.JsonDataUtils#provideCustomerDeposit")
    @Tag("regression")
    @Story("Deposit Funds")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test logs in a customer, deposits funds into their account, and verifies the success message.")
    public void testCustomerDeposit(String userName, String amount, String expectedMessage) {
        CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
        CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                .clickLogin();

        CustomerDepositPage depositPage = accountPage.clickDepositTab();
        depositPage.enterAmount(amount)
                .clickDepositSubmit();

        assertEquals(expectedMessage, depositPage.getMessage(), "Expected successful deposit message");
    }
}
