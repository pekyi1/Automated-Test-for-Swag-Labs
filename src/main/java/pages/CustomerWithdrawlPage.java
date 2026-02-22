package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class CustomerWithdrawlPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "form[ng-submit='withdrawl()'] input[ng-model='amount']")
    private WebElement amountInput;

    @FindBy(css = "form[ng-submit='withdrawl()'] button[type='submit']")
    private WebElement withdrawSubmitBtn;

    @FindBy(css = "span.error")
    private WebElement messageLabel;

    public CustomerWithdrawlPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public CustomerWithdrawlPage enterAmount(String amount) {
        waitUtils.waitForVisibility(amountInput);
        amountInput.clear();
        amountInput.sendKeys(amount);
        return this;
    }

    public void clickWithdrawSubmit() {
        waitUtils.safeClick(withdrawSubmitBtn);
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
