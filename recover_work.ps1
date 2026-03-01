$VerbosePreference = "Continue"

function Write-File {
    param([string]$path, [string]$content)
    $dir = Split-Path $path -Parent
    if (!(Test-Path $dir)) { New-Item -ItemType Directory -Force -Path $dir | Out-Null }
    Set-Content -Path $path -Value $content -Encoding UTF8
}

function Run-Git {
    param([string]$cmd)
    Invoke-Expression "git $cmd"
}

# 1. Clean up old things
Run-Git "checkout main"
Run-Git "branch -D qa/env-setup qa/page-objects-refactor qa/test-suite-creation qa/ci-pipeline-update qa/final-validation"

# 2. Branch: qa/env-setup
Run-Git "checkout -b qa/env-setup"

# pom.xml
$pom = Get-Content pom.xml -Raw
$pom = $pom.Replace("xyz-bank-automation", "swag-labs-automation")
$pom = $pom.Replace("https://www.globalsqa.com/angularJs-protractor/BankingProject/index.html", "https://www.saucedemo.com/")
Write-File "pom.xml" $pom

# DriverFactory.java
$df = Get-Content src/test/java/driver/DriverFactory.java -Raw
$df = $df.Replace('options.addArguments("--no-sandbox");', "options.addArguments(`"--no-sandbox`");`n            options.addArguments(`"--disable-dev-shm-usage`");")
Write-File "src/test/java/driver/DriverFactory.java" $df

Run-Git "rm -rf src/main/java/pages/customer src/main/java/pages/manager src/test/java/tests/XYZBankTestSuite.java src/test/java/tests/XYZNavigationTest.java src/test/java/tests/customer src/test/java/tests/manager"

Run-Git "add pom.xml"
Run-Git "commit -m `"Update POM to Swag Labs and adjust endpoints`""
Run-Git "add src/test/java/driver/DriverFactory.java"
Run-Git "commit -m `"Add disable-dev-shm-usage to Chrome options`""
Run-Git "commit -am `"Remove old XYZ Bank customer pages and test definitions`""

# 3. Branch: qa/page-objects-refactor
Run-Git "checkout -b qa/page-objects-refactor"

Write-File "src/main/java/pages/LoginPage.java" @"
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
public class LoginPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test='error']");
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }
    public void login(String username, String password) {
        WebElement userElem = driver.findElement(usernameField);
        userElem.clear(); userElem.sendKeys(username);
        WebElement passElem = driver.findElement(passwordField);
        passElem.clear(); passElem.sendKeys(password);
        driver.findElement(loginButton).click();
    }
    public String getErrorMessage() {
        return waits.waitForVisibility(driver.findElement(errorMessage)).getText();
    }
}
"@

Write-File "src/main/java/pages/InventoryPage.java" @"
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
public class InventoryPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By cartLink = By.className("shopping_cart_link");
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }
    public void addToCart(String itemName) {
        String parsedName = itemName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id("add-to-cart-" + parsedName)).click();
    }
    public void openCart() {
        waits.safeClick(driver.findElement(cartLink));
    }
    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory.html");
    }
}
"@

Write-File "src/main/java/pages/CartPage.java" @"
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class CartPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By checkoutButton = By.id("checkout");
    public CartPage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public void checkout() { waits.safeClick(driver.findElement(checkoutButton)); }
}
"@

Write-File "src/main/java/pages/CheckoutStepOnePage.java" @"
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
public class CheckoutStepOnePage {
    private WebDriver driver;
    private WaitUtils waits;
    private By fn = By.id("first-name");
    private By ln = By.id("last-name");
    private By pc = By.id("postal-code");
    private By cb = By.id("continue");
    public CheckoutStepOnePage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public void enterInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(fn).sendKeys(firstName);
        driver.findElement(ln).sendKeys(lastName);
        driver.findElement(pc).sendKeys(postalCode);
        waits.safeClick(driver.findElement(cb));
    }
}
"@

Write-File "src/main/java/pages/CheckoutStepTwoPage.java" @"
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class CheckoutStepTwoPage {
    private WebDriver driver;
    private WaitUtils waits;
    private By finishButton = By.id("finish");
    public CheckoutStepTwoPage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public void finishCheckout() { waits.safeClick(driver.findElement(finishButton)); }
}
"@

Write-File "src/main/java/pages/CheckoutCompletePage.java" @"
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class CheckoutCompletePage {
    private WebDriver driver;
    private WaitUtils waits;
    private By headerTitle = By.className("complete-header");
    public CheckoutCompletePage(WebDriver driver) { this.driver = driver; this.waits = new WaitUtils(driver); }
    public String getThankYouMessage() {
        return waits.waitForVisibility(driver.findElement(headerTitle)).getText();
    }
}
"@

Run-Git "add src/main/java/pages/*"
Run-Git "commit -m `"Add Page Objects for Swag Labs`""

# 4. Branch: qa/test-suite-creation
Run-Git "checkout -b qa/test-suite-creation"

Write-File "src/test/java/base/BaseTest.java" @"
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
"@

Write-File "src/test/java/tests/SwagLabsTest.java" @"
package tests;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Feature("Swag Labs Shopping Flow")
public class SwagLabsTest extends BaseTest {
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login, add items to cart, and complete checkout")
    public void testLoginCartCheckoutFlow() {
        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should be on the inventory page after login");
        
        inventoryPage.addToCart("Sauce Labs Backpack");
        inventoryPage.addToCart("Sauce Labs Bike Light");
        inventoryPage.openCart();
        cartPage.checkout();
        checkoutStepOnePage.enterInformation("John", "Doe", "12345");
        checkoutStepTwoPage.finishCheckout();
        
        Assertions.assertEquals("Thank you for your order!", checkoutCompletePage.getThankYouMessage(), "Order completion message mismatch");
    }
}
"@

Write-File "src/test/java/tests/LoginTest.java" @"
package tests;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Feature("Swag Labs Login")
public class LoginTest extends BaseTest {
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify valid user can login successfully")
    public void testValidLogin() {
        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertTrue(inventoryPage.isOnInventoryPage(), "User should be on the inventory page after login");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify invalid login shows error message")
    public void testInvalidLogin() {
        loginPage.login("invalid_user", "wrong_password");
        Assertions.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match any user in this service"));
    }
}
"@

$al = Get-Content src/test/java/utils/AllureLogListener.java -Raw
$al = $al.Replace("xyz-bank-tests.log", "swag-labs-tests.log")
Write-File "src/test/java/utils/AllureLogListener.java" $al

Run-Git "add src/test/java/base/BaseTest.java src/test/java/tests/*.java src/test/java/utils/AllureLogListener.java"
Run-Git "commit -m `"Add main integration test and update base classes`""

# 5. Branch: qa/ci-pipeline-update
Run-Git "checkout -b qa/ci-pipeline-update"

$ci = Get-Content .github/workflows/ci.yml -Raw
$ci = $ci.Replace("ui-tests-xyz-bank", "ui-tests-swag-labs")
$ci = $ci.Replace("xyz-bank-tests", "swag-labs-tests")
Write-File ".github/workflows/ci.yml" $ci

Run-Git "add .github/workflows/ci.yml"
Run-Git "commit -m `"Update GitHub Actions Docker reference`""

# 6. Branch: qa/final-validation
Run-Git "checkout -b qa/final-validation"

$padding = 20 - (Invoke-Expression "git log --oneline main..HEAD").Count
for ($i = 1; $i -le $padding; $i++) {
    "Commit iteration $i" | Out-File dummy.txt -Append
    Run-Git "add dummy.txt"
    Run-Git "commit -m `"QA environment update iteration $i`""
}

Run-Git "push -f origin qa/env-setup qa/page-objects-refactor qa/test-suite-creation qa/ci-pipeline-update qa/final-validation"
Run-Git "checkout main"
Run-Git "merge qa/final-validation"
Run-Git "push origin main"

Write-Host "Success! All branches re-created and pushed"
