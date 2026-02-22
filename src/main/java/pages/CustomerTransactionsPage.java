package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

import java.util.List;

public class CustomerTransactionsPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "button[ng-click='back()']")
    private WebElement backBtn;

    @FindBy(css = "button[ng-click='reset()']")
    private WebElement resetBtn;

    @FindBy(css = "table.table-bordered tbody tr")
    private List<WebElement> transactionRows;

    public CustomerTransactionsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public CustomerAccountPage clickBack() {
        waitUtils.safeClick(backBtn);
        return new CustomerAccountPage(driver);
    }

    public CustomerTransactionsPage clickReset() {
        waitUtils.safeClick(resetBtn);
        return this;
    }

    public int getTransactionCount() {
        try {
            java.lang.Thread.sleep(500); // Wait for potential table rendering
        } catch (InterruptedException ignored) {
        }
        return transactionRows.size();
    }

    public boolean isTransactionPresent(String expectedAmount, String expectedType) {
        try {
            java.lang.Thread.sleep(1000); // Wait for transactions to load
        } catch (InterruptedException ignored) {
        }
        for (WebElement row : transactionRows) {
            List<WebElement> cols = row.findElements(org.openqa.selenium.By.tagName("td"));
            if (cols.size() >= 3) {
                String amount = cols.get(1).getText();
                String type = cols.get(2).getText();
                if (amount.equals(expectedAmount) && type.equalsIgnoreCase(expectedType)) {
                    return true;
                }
            }
        }
        return false;
    }
}
