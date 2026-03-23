package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void safeClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            Thread.sleep(250);
        } catch (Exception e) {
        }
        try {
            element.click();
        } catch (Exception e) {
            // fallback to JS execution if standard click gets intercepted
            org.openqa.selenium.JavascriptExecutor jse = (org.openqa.selenium.JavascriptExecutor) this.driver;
            jse.executeScript("arguments[0].click();", element);
        }
    }
}
