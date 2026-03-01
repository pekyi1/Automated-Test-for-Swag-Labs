package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

import java.util.List;

public class CheckoutStepTwoPage {
    private WebDriver driver;
    private WaitUtils waits;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = ".summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(css = ".summary_tax_label")
    private WebElement taxLabel;

    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;

    @FindBy(css = ".cart_item")
    private List<WebElement> summaryItems;

    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void finishCheckout() {
        waits.safeClick(finishButton);
    }

    public void cancelCheckout() {
        waits.safeClick(cancelButton);
    }

    public String getSubtotal() {
        return waits.waitForVisibility(subtotalLabel).getText();
    }

    public String getTax() {
        return waits.waitForVisibility(taxLabel).getText();
    }

    public String getTotal() {
        return waits.waitForVisibility(totalLabel).getText();
    }

    public int getSummaryItemsCount() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        return summaryItems.size();
    }

    public boolean isOnCheckoutStepTwoPage() {
        return driver.getCurrentUrl().contains("checkout-step-two.html");
    }
}
