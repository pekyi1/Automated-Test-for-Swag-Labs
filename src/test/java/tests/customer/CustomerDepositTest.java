package tests.customer;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.CustomerAccountPage;
import pages.CustomerDepositPage;
import pages.CustomerLoginPage;

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
public class CustomerDepositTest extends BaseTest {

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerDeposit")
        @Tag("regression")
        @Story("Deposit Funds")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test logs in a customer, deposits funds into their account, and verifies the success message.")
        public void testCustomerDeposit(String userName, String amount, String expectedMessage) {
                // Step 1: Login as customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Step 2: Get initial balance
                int initialBalance = accountPage.getAccountBalance();

                // Step 3: Navigate to Deposit tab and submit deposit
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(amount)
                                .clickDepositSubmit();

                // Step 4 & 5: Verify success message and balance increased
                int finalBalance = accountPage.getAccountBalance();
                int depositAmountInt = Integer.parseInt(amount);

                assertAll("Deposit valid checks",
                                () -> assertEquals(expectedMessage, depositPage.getMessage(),
                                                "Expected successful deposit message"),
                                () -> assertFalse(depositPage.getMessageClass().contains("error"),
                                                "Expected success message to not have an error class"),
                                () -> assertEquals(initialBalance + depositAmountInt, finalBalance,
                                                "Expected account balance to increase by deposit amount"));
        }

        @ParameterizedTest
        @MethodSource("utils.JsonDataUtils#provideCustomerDepositNegative")
        @Tag("regression")
        @Story("Deposit Funds")
        @Severity(SeverityLevel.CRITICAL)
        @Description("This test verifies that zero or negative deposits are rejected and balance remains unchanged.")
        public void testCustomerDepositNegative(String userName, String amount, String expectedMessage) {
                // Step 1: Login as customer
                CustomerLoginPage customerLoginPage = loginPage.clickCustomerLogin();
                CustomerAccountPage accountPage = customerLoginPage.selectUser(userName)
                                .clickLogin();

                // Step 2: Get initial balance
                int initialBalance = accountPage.getAccountBalance();

                // Step 3: Navigate to Deposit tab and submit invalid deposit
                CustomerDepositPage depositPage = accountPage.clickDepositTab();
                depositPage.enterAmount(amount)
                                .clickDepositSubmit();

                // Step 4 & 5: Verify error message and balance remains unchanged
                int finalBalance = accountPage.getAccountBalance();

                assertAll("Deposit invalid checks",
                                () -> assertEquals(expectedMessage, depositPage.getMessage(),
                                                "Expected error message for invalid deposit"),
                                () -> assertTrue(depositPage.getMessageClass().contains("error"),
                                                "Expected message to have an error class"),
                                () -> assertEquals(initialBalance, finalBalance,
                                                "Expected account balance to remain unchanged"));
        }
}
