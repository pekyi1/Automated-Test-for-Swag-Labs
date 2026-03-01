package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            // Important for running Chrome in Docker/CI containers safely
            options.addArguments("--no-sandbox");
        }

        WebDriver driver = new ChromeDriver(options);
        // Implicit wait removed to prevent clashes with Explicit Wait
        driver.manage().window().maximize();

        return driver;
    }
}
