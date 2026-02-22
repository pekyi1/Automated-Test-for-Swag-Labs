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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
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
                // Step 1: Login as customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Step 2: Deposit first so withdrawal succeeds
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(depositAmount)
                                .clickDepositSubmit();

                // Step 3: Navigate to Withdrawl tab
                CustomerWithdrawlPage withdrawPage = accountPage.clickWithdrawlTab();

                // Wait briefly for balance to update visually in the app state
                try {
                        java.lang.Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                // Step 4: Get initial balance before withdrawal
                int initialBalance = accountPage.getAccountBalance();

                // Step 5: Submit withdrawal amount
                withdrawPage.enterAmount(withdrawalAmount)
                                .clickWithdrawSubmit();

                try {
                        java.lang.Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                // Step 6 & 7: Verify success message and balance decreased
                int finalBalance = accountPage.getAccountBalance();
                int withdrawalAmountInt = Integer.parseInt(withdrawalAmount);

                assertAll("Withdrawal valid checks",
                                () -> assertEquals(expectedMessage, withdrawPage.getMessage(),
                                                "Expected successful withdrawal message"),
                                () -> assertFalse(withdrawPage.getMessageClass().contains("error"),
                                                "Expected success message to not have an error class"),
                                () -> assertEquals(initialBalance - withdrawalAmountInt, finalBalance,
                                                "Expected account balance to decrease by withdrawal amount"));
        }

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerWithdrawalNegative")
        @Tag("regression")
        @Story("Withdraw Funds")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test verifies that zero or negative withdrawals are rejected and balance remains unchanged.")
        public void testCustomerWithdrawalNegative(String userName, String depositAmount, String withdrawalAmount,
                        String expectedMessage) {
                // Step 1: Login as customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Step 2: Deposit initial funds
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(depositAmount)
                                .clickDepositSubmit();

                // Step 3: Navigate to Withdrawl tab
                CustomerWithdrawlPage withdrawPage = accountPage.clickWithdrawlTab();

                try {
                        java.lang.Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                // Step 4: Get initial balance before withdrawal
                int initialBalance = accountPage.getAccountBalance();

                // Step 5: Submit invalid withdrawal amount
                withdrawPage.enterAmount(withdrawalAmount)
                                .clickWithdrawSubmit();

                try {
                        java.lang.Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                // Step 6 & 7: Verify no success message appears and balance remains unchanged
                int finalBalance = accountPage.getAccountBalance();

                assertAll("Withdrawal invalid checks",
                                () -> assertEquals(expectedMessage, withdrawPage.getMessage(),
                                                "Expected validation message for invalid withdrawal"),
                                () -> assertTrue(withdrawPage.getMessageClass().contains("error"),
                                                "Expected validation message to have an error class"),
                                () -> assertEquals(initialBalance, finalBalance,
                                                "Expected account balance to remain unchanged"));
        }

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerWithdrawalMoreThanBalance")
        @Tag("regression")
        @Story("Withdraw Funds")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test verifies that withdrawals exceeding the balance are rejected.")
        public void testCustomerWithdrawalMoreThanBalance(String userName, String depositAmount,
                        String withdrawalAmount,
                        String expectedMessage) {
                // Step 1: Login as customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Step 2: Deposit initial funds
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(depositAmount)
                                .clickDepositSubmit();

                // Step 3: Navigate to Withdrawl tab
                CustomerWithdrawlPage withdrawPage = accountPage.clickWithdrawlTab();

                try {
                        java.lang.Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                // Step 4: Get initial balance before withdrawal
                int initialBalance = accountPage.getAccountBalance();

                // Step 5: Submit excessive withdrawal amount
                withdrawPage.enterAmount(withdrawalAmount)
                                .clickWithdrawSubmit();

                try {
                        java.lang.Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                // Step 6 & 7: Verify overdraft rejection message and balance remains unchanged
                int finalBalance = accountPage.getAccountBalance();

                assertAll("Withdrawal overdraft checks",
                                () -> assertEquals(expectedMessage, withdrawPage.getMessage(),
                                                "Expected overdraft rejection message"),
                                () -> assertTrue(withdrawPage.getMessageClass().contains("error"),
                                                "Expected validation message to have an error class"),
                                () -> assertEquals(initialBalance, finalBalance,
                                                "Expected account balance to remain unchanged"));
        }
}
