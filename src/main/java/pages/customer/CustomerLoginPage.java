package pages.customer;

import pages.LoginPage;
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

    public boolean isUserDropdownVisibleFast() {
        try {
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofMillis(50));
            boolean isVisible = !driver.findElements(org.openqa.selenium.By.id("userSelect")).isEmpty();
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));
            return isVisible;
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));
            return false;
        }
    }

    public CustomerLoginPage selectUser(String userName) {
        waitUtils.waitForVisibility(userSelectDropdown);
        Select select = new Select(userSelectDropdown);
        select.selectByVisibleText(userName);
        return this;
    }

    public boolean isUserInDropdown(String userName) {
        if (!isUserDropdownVisibleFast()) {
            return false;
        }
        waitUtils.waitForVisibility(userSelectDropdown);
        Select select = new Select(userSelectDropdown);
        for (WebElement option : select.getOptions()) {
            if (option.getText().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLoginButtonVisible() {
        try {
            // Find elements by the same CSS selector. If list is empty, it doesn't exist.
            // This briefly relies on the 2s implicit wait still, but we can temporarily
            // override it
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofMillis(50));
            boolean isVisible = !driver
                    .findElements(org.openqa.selenium.By.cssSelector("button.btn-default[type='submit']")).isEmpty();
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));
            return isVisible;
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));
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
