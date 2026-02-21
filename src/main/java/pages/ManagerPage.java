package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class ManagerPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "button[ng-click='addCust()']")
    private WebElement addCustomerTab;

    @FindBy(css = "button[ng-click='openAccount()']")
    private WebElement openAccountTab;

    @FindBy(css = "button[ng-click='showCust()']")
    private WebElement customersTab;

    @FindBy(css = "button.btn.home[ng-click='home()']")
    private WebElement homeBtn;

    public ManagerPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean areManagerTabsVisible() {
        try {
            waitUtils.waitForVisibility(addCustomerTab);
            waitUtils.waitForVisibility(openAccountTab);
            waitUtils.waitForVisibility(customersTab);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AddCustomerPage clickAddCustomerTab() {
        waitUtils.safeClick(addCustomerTab);
        return new AddCustomerPage(driver);
    }

    public OpenAccountPage clickOpenAccountTab() {
        waitUtils.safeClick(openAccountTab);
        return new OpenAccountPage(driver);
    }

    public CustomersPage clickCustomersTab() {
        waitUtils.safeClick(customersTab);
        return new CustomersPage(driver);
    }

    public LoginPage clickHomeBtn() {
        waitUtils.safeClick(homeBtn);
        return new LoginPage(driver);
    }
}
