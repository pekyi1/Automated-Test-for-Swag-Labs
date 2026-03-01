package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class LoginPage {
    private WebDriver driver;
    private WaitUtils waits;

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        waits.waitForVisibility(usernameField).clear();
        usernameField.sendKeys(username);

        waits.waitForVisibility(passwordField).clear();
        passwordField.sendKeys(password);

        waits.safeClick(loginButton);
    }

    public String getErrorMessage() {
        return waits.waitForVisibility(errorMessage).getText();
    }

    public boolean isOnLoginPage() {
        return driver.findElement(org.openqa.selenium.By.id("login-button")).isDisplayed();
    }
}
