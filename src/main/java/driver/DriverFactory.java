package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        String headless = System.getProperty("headless", "false");
        if (Boolean.parseBoolean(headless)) {
            options.addArguments("--headless=new");
        }

        WebDriver driver = new ChromeDriver(options);
        // Implicit wait of 2 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();

        return driver;
    }
}
