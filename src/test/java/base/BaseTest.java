package base;

import driver.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPage;
import utils.ScreenshotListener;

@ExtendWith(ScreenshotListener.class)
public abstract class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    private String url;

    public WebDriver getDriver() {
        return driver;
    }

    protected String getUrl() {
        url = System.getProperty("baseUrl",
                System.getenv().getOrDefault("APP_BASE_URL",
                        "https://www.globalsqa.com/angularJs-protractor/BankingProject/index.html"));
        Assertions.assertNotNull(url);

        return url;
    }

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(getUrl());
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
