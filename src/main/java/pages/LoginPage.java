package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
public class LoginPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test='error']");
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }
    public void login(String username, String password) {
        WebElement userElem = driver.findElement(usernameField);
        userElem.clear(); userElem.sendKeys(username);
        WebElement passElem = driver.findElement(passwordField);
        passElem.clear(); passElem.sendKeys(password);
        driver.findElement(loginButton).click();
    }
    public String getErrorMessage() {
        return waits.waitForVisibility(driver.findElement(errorMessage)).getText();
    }
}
