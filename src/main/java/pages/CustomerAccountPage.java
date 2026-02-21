package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class CustomerAccountPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "button[ng-click='transactions()']")
    private WebElement transactionsTab;

    @FindBy(css = "button[ng-click='deposit()']")
    private WebElement depositTab;

    @FindBy(css = "button[ng-click='withdrawl()']")
    private WebElement withdrawlTab; // Note: typo 'withdrawl' is common in this app

    @FindBy(css = "button[ng-click='byebye()']")
    private WebElement logoutBtn;

    @FindBy(css = "span.fontBig")
    private WebElement welcomeNameText;

    @FindBy(css = "button[ng-click='home()']")
    private WebElement homeBtn;

    public CustomerAccountPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public CustomerTransactionsPage clickTransactionsTab() {
        waitUtils.safeClick(transactionsTab);
        return new CustomerTransactionsPage(driver);
    }

    public CustomerDepositPage clickDepositTab() {
        waitUtils.safeClick(depositTab);
        return new CustomerDepositPage(driver);
    }

    public CustomerWithdrawlPage clickWithdrawlTab() {
        waitUtils.safeClick(withdrawlTab);
        return new CustomerWithdrawlPage(driver);
    }

    public CustomerLoginPage clickLogout() {
        waitUtils.safeClick(logoutBtn);
        return new CustomerLoginPage(driver);
    }

    public String getWelcomeName() {
        waitUtils.waitForVisibility(welcomeNameText);
        return welcomeNameText.getText();
    }

    public LoginPage clickHomeBtn() {
        waitUtils.safeClick(homeBtn);
        return new LoginPage(driver);
    }
}
