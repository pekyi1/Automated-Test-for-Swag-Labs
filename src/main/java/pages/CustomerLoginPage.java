package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.WaitUtils;

public class CustomerLoginPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(id = "userSelect")
    private WebElement userSelectDropdown;

    @FindBy(css = "button[ng-click='home()']")
    private WebElement homeBtn;

    @FindBy(css = "button.btn-default[type='submit']")
    private WebElement loginBtn;

    public CustomerLoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isUserDropdownVisible() {
        try {
            waitUtils.waitForVisibility(userSelectDropdown);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CustomerLoginPage selectUser(String userName) {
        waitUtils.waitForVisibility(userSelectDropdown);
        Select select = new Select(userSelectDropdown);
        select.selectByVisibleText(userName);
        return this;
    }

    public boolean isLoginButtonVisible() {
        try {
            return loginBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public CustomerAccountPage clickLogin() {
        waitUtils.safeClick(loginBtn);
        return new CustomerAccountPage(driver);
    }

    public LoginPage clickHomeBtn() {
        waitUtils.safeClick(homeBtn);
        return new LoginPage(driver);
    }
}
