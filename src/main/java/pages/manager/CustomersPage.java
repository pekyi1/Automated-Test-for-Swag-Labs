package pages.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;
import java.util.List;

public class CustomersPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "input[ng-model='searchCustomer']")
    private WebElement searchInput;

    @FindBy(css = "table.table-bordered tbody tr[ng-repeat^='cust']")
    private List<WebElement> customerRows;

    @FindBy(css = "table.table-bordered tbody tr td:first-child")
    private List<WebElement> firstNameCells;

    @FindBy(css = "table.table-bordered tbody tr td:nth-child(2)")
    private List<WebElement> lastNameCells;

    @FindBy(css = "table.table-bordered tbody tr button[ng-click^='deleteCust']")
    private List<WebElement> deleteButtons;

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public CustomersPage searchCustomer(String searchText) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(searchText);
        return this;
    }

    public int getCustomerCount() {
        try {
            // Short rest to let angular filter results
            java.lang.Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        return customerRows.size();
    }

    public boolean isCustomerInList(String name) {
        for (int i = 0; i < customerRows.size(); i++) {
            if (firstNameCells.get(i).getText().equals(name) || lastNameCells.get(i).getText().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void deleteCustomer(String name) {
        for (int i = 0; i < customerRows.size(); i++) {
            if (firstNameCells.get(i).getText().equals(name) || lastNameCells.get(i).getText().equals(name)) {
                waitUtils.safeClick(deleteButtons.get(i));
                break;
            }
        }
    }
}
