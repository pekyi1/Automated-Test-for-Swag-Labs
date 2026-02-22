package tests.customer;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomerAccountPage;
import pages.CustomerDepositPage;
import pages.CustomerLoginPage;
import pages.CustomerTransactionsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Customer Portal")
@Feature("Transactions History")
public class CustomerTransactionsTest extends BaseTest {

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerDeposit")
        @Tag("regression")
        @Story("View Transaction History")
        @Severity(SeverityLevel.NORMAL)
        @Description("This test verifies that after making a deposit, the transaction is recorded and visible in the history.")
        public void testCustomerTransactionHistory(String userName, String amount, String expectedMessage) {
                // Step 1: Login as customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Step 2: Perform a deposit
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(amount)
                                .clickDepositSubmit();

                // Step 3: Navigate to transactions
                CustomerTransactionsPage transactionsPage = accountPage.clickTransactionsTab();

                // Wait for the backend to record the transaction
                try {
                        java.lang.Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }

                // Step 4: Verify the deposit transaction is present in history
                boolean transactionFound = transactionsPage.isTransactionPresent(amount, "Credit");
                assertAll("Transaction history checks",
                                () -> assertTrue(transactionFound,
                                                "Expected a Credit transaction of amount " + amount
                                                                + " to be visible in history."));
        }
}
