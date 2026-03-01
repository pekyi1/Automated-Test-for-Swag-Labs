package base;
import driver.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.*;
import utils.ScreenshotListener;

@ExtendWith(ScreenshotListener.class)
public abstract class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutStepOnePage checkoutStepOnePage;
    protected CheckoutStepTwoPage checkoutStepTwoPage;
    protected CheckoutCompletePage checkoutCompletePage;

    public WebDriver getDriver() { return driver; }

    protected String getUrl() {
        String url = System.getProperty("baseUrl", System.getenv().getOrDefault("APP_BASE_URL", "https://www.saucedemo.com/"));
        Assertions.assertNotNull(url);
        return url;
    }

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(getUrl());
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @AfterEach
    public void tearDown() { if (driver != null) { driver.quit(); } }
}
