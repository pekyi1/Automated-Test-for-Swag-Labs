package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
public class CheckoutStepOnePage {
    private WebDriver driver;
    private WaitUtils waits;
    private By fn = By.id("first-name");
    private By ln = By.id("last-name");
    private By pc = By.id("postal-code");
    private By cb = By.id("continue");
    public CheckoutStepOnePage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public void enterInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(fn).sendKeys(firstName);
        driver.findElement(ln).sendKeys(lastName);
        driver.findElement(pc).sendKeys(postalCode);
        waits.safeClick(driver.findElement(cb));
    }
}
