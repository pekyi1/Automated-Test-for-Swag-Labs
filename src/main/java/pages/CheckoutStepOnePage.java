package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class CheckoutStepOnePage {
    private WebDriver driver;
    private WaitUtils waits;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterInformation(String firstName, String lastName, String postalCode) {
        waits.waitForVisibility(firstNameField).clear();
        firstNameField.sendKeys(firstName);

        waits.waitForVisibility(lastNameField).clear();
        lastNameField.sendKeys(lastName);

        waits.waitForVisibility(postalCodeField).clear();
        postalCodeField.sendKeys(postalCode);

        waits.safeClick(continueButton);
    }

    public void cancelCheckout() {
        waits.safeClick(cancelButton);
    }

    public String getErrorMessage() {
        return waits.waitForVisibility(errorMessage).getText();
    }

    public boolean isOnCheckoutStepOnePage() {
        return driver.getCurrentUrl().contains("checkout-step-one.html");
    }
}
