package base;

import driver.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPage;
import utils.ScreenshotListener;

@ExtendWith(ScreenshotListener.class)
public abstract class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver();

        String baseUrl = System.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getenv("BASE_URL");
        }
        if (baseUrl == null || baseUrl.isEmpty()) {
            // Default value from pom.xml should be loaded, but hardcoding fallback here
            // just in case
            baseUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/index.html";
        }

        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
