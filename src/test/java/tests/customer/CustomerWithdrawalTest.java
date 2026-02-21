package tests.customer;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomerAccountPage;
import pages.CustomerDepositPage;
import pages.CustomerLoginPage;
import pages.CustomerWithdrawlPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Customer Portal")
@Feature("Transactions")
public class CustomerWithdrawalTest extends BaseTest {

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerWithdrawal")
        @Tag("regression")
        @Story("Withdraw Funds")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test logs in a customer, deposits initial funds, withdraws an amount, and verifies the success message.")
        public void testCustomerWithdrawal(String userName, String depositAmount, String withdrawalAmount,
                        String expectedMessage) {
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Deposit first so withdrawal succeeds
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(depositAmount)
                                .clickDepositSubmit();

                CustomerWithdrawlPage withdrawPage = accountPage.clickWithdrawlTab();

                // Wait briefly for balance to update visually in the app state
                try {
                        java.lang.Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                withdrawPage.enterAmount(withdrawalAmount)
                                .clickWithdrawSubmit();

                assertEquals(expectedMessage, withdrawPage.getMessage(), "Expected successful withdrawal message");
        }
}
