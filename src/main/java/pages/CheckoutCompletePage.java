package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class CheckoutCompletePage {
    private WebDriver driver;
    private WaitUtils waits;

    @FindBy(css = ".complete-header")
    private WebElement headerTitle;

    @FindBy(css = ".complete-text")
    private WebElement completeText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public String getThankYouMessage() {
        return waits.waitForVisibility(headerTitle).getText();
    }

    public String getCompleteText() {
        return waits.waitForVisibility(completeText).getText();
    }

    public void goBackToProducts() {
        waits.safeClick(backToProductsButton);
    }

    public boolean isOnCheckoutCompletePage() {
        return driver.getCurrentUrl().contains("checkout-complete.html");
    }
}
