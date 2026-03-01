package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class CheckoutCompletePage {
    private WebDriver driver;
    private WaitUtils waits;
    private By headerTitle = By.className("complete-header");
    public CheckoutCompletePage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public String getThankYouMessage() {
        return waits.waitForVisibility(driver.findElement(headerTitle)).getText();
    }
}
