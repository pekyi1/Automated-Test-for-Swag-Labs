package pages.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class AddCustomerPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(css = "input[ng-model='fName']")
    private WebElement firstNameInput;

    @FindBy(css = "input[ng-model='lName']")
    private WebElement lastNameInput;

    @FindBy(css = "input[ng-model='postCd']")
    private WebElement postCodeInput;

    @FindBy(css = "form[name='myForm'] button[type='submit']")
    private WebElement addCustomerSubmitBtn;

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public AddCustomerPage enterFirstName(String firstName) {
        waitUtils.waitForVisibility(firstNameInput);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public AddCustomerPage enterLastName(String lastName) {
        waitUtils.waitForVisibility(lastNameInput);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        return this;
    }

    public AddCustomerPage enterPostCode(String postCode) {
        waitUtils.waitForVisibility(postCodeInput);
        postCodeInput.clear();
        postCodeInput.sendKeys(postCode);
        return this;
    }

    public void clickAddCustomerSubmit() {
        waitUtils.safeClick(addCustomerSubmitBtn);
    }

    public String getAlertTextAndAccept() {
        // Wait briefly for alert to appear
        try {
            java.lang.Thread.sleep(500);
            org.openqa.selenium.Alert alert = driver.switchTo().alert();
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (Exception e) {
            return null;
        }
    }
}
