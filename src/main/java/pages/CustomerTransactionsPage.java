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
        long endTime = System.currentTimeMillis() + 10000; // 10 second timeout for CI runners

        while (System.currentTimeMillis() < endTime) {
            try {
                // Fetch rows cleanly on each iteration to guarantee we aren't using a cached
                // empty list
                List<WebElement> rows = driver
                        .findElements(org.openqa.selenium.By.cssSelector("table.table-bordered tbody tr"));
                for (WebElement row : rows) {
                    List<WebElement> cols = row.findElements(org.openqa.selenium.By.tagName("td"));
                    if (cols.size() >= 3) {
                        String amount = cols.get(1).getText().trim();
                        String type = cols.get(2).getText().trim();
                        if (amount.equals(expectedAmount) && type.equalsIgnoreCase(expectedType)) {
                            return true;
                        }
                    }
                }
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // Ignore stale element exceptions as Angular regenerates the DOM frequently
            }

            try {
                java.lang.Thread.sleep(500); // Polling interval
            } catch (InterruptedException ignored) {
            }
        }
        return false;
    }
}
