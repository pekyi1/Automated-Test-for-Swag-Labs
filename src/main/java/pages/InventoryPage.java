package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
public class InventoryPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By cartLink = By.className("shopping_cart_link");
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }
    public void addToCart(String itemName) {
        String parsedName = itemName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id("add-to-cart-" + parsedName)).click();
    }
    public void openCart() {
        waits.safeClick(driver.findElement(cartLink));
    }
    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory.html");
    }
}
