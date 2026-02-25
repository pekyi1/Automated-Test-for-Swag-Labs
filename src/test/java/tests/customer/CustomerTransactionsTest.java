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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Digital Banking Application")
@Feature("Customer \u2014 Transactions & Funds Management")
public class CustomerTransactionsTest extends BaseTest {

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerDeposit")
        @Tag("regression")
        @Tag("smoke")
        @Story("US2: View transactions")
        @Severity(SeverityLevel.NORMAL)
        @Description("Customers should be able to view a list of their recent transactions.")
        public void testCustomerTransactionHistory(String userName, String amount, String expectedMessage) {
                // Step 1: Login as customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Step 2: Perform a deposit
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(amount)
                                .clickDepositSubmit();

                // Wait for the specific success message to ensure the backend processed it
                assertEquals(expectedMessage, depositPage.getMessage(),
                                "Expected successful deposit before checking history");

                // Step 3: Navigate to transactions
                try {
                        Thread.sleep(2000); // Wait for the backend to process the deposit
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                }
                CustomerTransactionsPage transactionsPage = accountPage.clickTransactionsTab();

                // Step 4: Verify the deposit transaction is present in history
                boolean transactionFound = transactionsPage.isTransactionPresent(amount, "Credit");
                assertAll("Transaction history checks",
                                () -> assertTrue(transactionFound,
                                                "Expected a Credit transaction of amount " + amount
                                                                + " to be visible in history."));
        }
}
