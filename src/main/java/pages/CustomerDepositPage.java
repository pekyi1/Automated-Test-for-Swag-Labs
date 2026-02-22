package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class CustomerDepositPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "form[ng-submit='deposit()'] input[ng-model='amount']")
    private WebElement amountInput;

    @FindBy(css = "form[ng-submit='deposit()'] button[type='submit']")
    private WebElement depositSubmitBtn;

    @FindBy(css = "span[ng-show='message']")
    private WebElement messageLabel;

    public CustomerDepositPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public CustomerDepositPage enterAmount(String amount) {
        waitUtils.waitForVisibility(amountInput);
        amountInput.clear();
        amountInput.sendKeys(amount);
        return this;
    }

    public void clickDepositSubmit() {
        waitUtils.safeClick(depositSubmitBtn);
    }

    public String getMessage() {
        try {
            waitUtils.waitForVisibility(messageLabel);
            return messageLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getMessageClass() {
        try {
            waitUtils.waitForVisibility(messageLabel);
            return messageLabel.getAttribute("class");
        } catch (Exception e) {
            return "";
        }
    }
}
