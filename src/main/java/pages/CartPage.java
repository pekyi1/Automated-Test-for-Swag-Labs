package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WaitUtils waits;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeBackpackButton;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void checkout() {
        waits.safeClick(checkoutButton);
    }

    public void continueShopping() {
        waits.safeClick(continueShoppingButton);
    }

    public void removeBackpack() {
        waits.safeClick(removeBackpackButton);
    }

    public int getCartItemsCount() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        return cartItems.size();
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart.html");
    }
}
