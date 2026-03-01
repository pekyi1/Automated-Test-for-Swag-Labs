package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class CartPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By checkoutButton = By.id("checkout");
    public CartPage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public void checkout() { waits.safeClick(driver.findElement(checkoutButton)); }
}
