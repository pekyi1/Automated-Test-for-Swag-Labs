package pages.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.WaitUtils;

public class OpenAccountPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(id = "userSelect")
    private WebElement customerDropdown;

    @FindBy(id = "currency")
    private WebElement currencyDropdown;

    @FindBy(css = "form[name='myForm'] button[type='submit']")
    private WebElement processBtn;

    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public OpenAccountPage selectCustomer(String customerName) {
        waitUtils.waitForVisibility(customerDropdown);
        Select select = new Select(customerDropdown);
        select.selectByVisibleText(customerName);
        return this;
    }

    public OpenAccountPage selectCurrency(String currency) {
        waitUtils.waitForVisibility(currencyDropdown);
        Select select = new Select(currencyDropdown);
        select.selectByVisibleText(currency);
        return this;
    }

    public void clickProcess() {
        waitUtils.safeClick(processBtn);
    }

    public String getAlertTextAndAccept() {
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
