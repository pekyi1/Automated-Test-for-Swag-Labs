package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class LoginPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "button[ng-click='customer()']")
    private WebElement customerLoginBtn;

    @FindBy(css = "button[ng-click='manager()']")
    private WebElement bankManagerLoginBtn;

    @FindBy(css = "button[ng-click='home()']")
    private WebElement homeBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public CustomerLoginPage clickCustomerLogin() {
        waitUtils.safeClick(customerLoginBtn);
        return new CustomerLoginPage(driver);
    }

    public ManagerPage clickBankManagerLogin() {
        waitUtils.safeClick(bankManagerLoginBtn);
        return new ManagerPage(driver);
    }

    public LoginPage clickHomeBtn() {
        waitUtils.safeClick(homeBtn);
        return this;
    }
}
