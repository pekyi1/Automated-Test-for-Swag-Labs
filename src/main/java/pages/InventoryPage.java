package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WaitUtils waits;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement openMenuButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsSidebarLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutSidebarLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutSidebarLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateSidebarLink;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartContainer;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".product_sort_container")
    private WebElement sortContainer;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpackButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeBackpackButton;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addBikeLightButton;

    @FindBy(id = "add-to-cart-sauce-labs-bolt-t-shirt")
    private WebElement addBoltTShirtButton;

    @FindBy(id = "remove-sauce-labs-bolt-t-shirt")
    private WebElement removeBoltTShirtButton;

    @FindBy(id = "add-to-cart-sauce-labs-fleece-jacket")
    private WebElement addFleeceJacketButton;

    @FindBy(id = "add-to-cart-sauce-labs-onesie")
    private WebElement addOnesieButton;

    @FindBy(id = "add-to-cart-test.allthethings()-t-shirt-(red)")
    private WebElement addTShirtRedButton;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void openMenu() {
        waits.safeClick(openMenuButton);
    }

    public void closeMenu() {
        waits.safeClick(closeMenuButton);
    }

    public void logout() {
        waits.safeClick(logoutSidebarLink);
    }

    public void resetAppState() {
        waits.safeClick(resetAppStateSidebarLink);
    }

    public void addBackpackToCart() {
        waits.safeClick(addBackpackButton);
    }

    public void removeBackpackFromCart() {
        waits.safeClick(removeBackpackButton);
    }

    public void addBikeLightToCart() {
        waits.safeClick(addBikeLightButton);
    }

    public void addBoltTShirtToCart() {
        waits.safeClick(addBoltTShirtButton);
    }

    public void removeBoltTShirtFromCart() {
        waits.safeClick(removeBoltTShirtButton);
    }

    public void openCart() {
        waits.safeClick(cartContainer);
    }

    public String getCartItemCount() {
        try {
            return waits.waitForVisibility(cartBadge).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public int getInventoryItemCount() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        return inventoryItems.size();
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    public void sortBy(String value) {
        waits.safeClick(sortContainer);
        sortContainer.sendKeys(value);
    }

    public boolean isCartBadgeDisplayed() {
        try {
            return cartBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
