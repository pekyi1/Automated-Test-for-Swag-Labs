package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class CheckoutStepTwoPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By finishButton = By.id("finish");
    public CheckoutStepTwoPage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public void finishCheckout() { waits.safeClick(driver.findElement(finishButton)); }
}
