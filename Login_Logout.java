
package Employee_Worktrack;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Login_Logout {


    // Application URL
    String url = "https://employee-worktrack-5lmu.vercel.app/login";

    // Locators
    String email_xpath = "//input[@type='email']";
    String password_xpath = "//input[@type='password']";
    String login_xpath = "//button[@type='submit']";
    String logout_xpath = "//button[contains(text(),'Logout')]";

    WebDriver driver;
    WebDriverWait wait;
    ChromeOptions options;

    // ==============================
    // SETUP
    // ==============================

    @BeforeTest
    public void setUp() {

    	options = new ChromeOptions();
    	options.addArguments("--headless=new");

        ChromeOptions options = new ChromeOptions();

        // Disable Chrome password manager
        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // Launch Chrome
        driver = new ChromeDriver(options);

        // Maximize browser
        driver.manage().window().maximize();

        // Explicit wait
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );

        // Open Login page
        driver.get(url);
    }

    // ==============================
    // LOGIN
    // ==============================

    @Test(priority = 1)
    public void testLogin() throws AWTException, InterruptedException {

        // ------------------------------
        // Enter Email
        // ------------------------------

        WebElement email = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(email_xpath)
                )
        );

        email.click();
        email.clear();
        email.sendKeys("test5@gmail.com");

        // ------------------------------
        // Enter Password
        // ------------------------------

        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(password_xpath)
                )
        );

        password.click();
        password.clear();
        password.sendKeys("Password@123");

        // ------------------------------
        // Print entered values
        // ------------------------------

        System.out.println(
                "Email entered = "
                + email.getAttribute("value")
        );

        System.out.println(
                "Password entered = "
                + password.getAttribute("value")
        );

        // ------------------------------
        // Login Button
        // ------------------------------

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(login_xpath)
                )
        );

        System.out.println(
                "Login button enabled = "
                + loginButton.isEnabled()
        );

        // ------------------------------
        // Click Login
        // ------------------------------

        loginButton.click();

        // Wait for application response
        Thread.sleep(3000);

        // ------------------------------
        // Print URL
        // ------------------------------

        String actURL = driver.getCurrentUrl();

        System.out.println(
                "Current URL after Login = "
                + actURL
        );

        System.out.println(
                "Page Title = "
                + driver.getTitle()
        );

        // ------------------------------
        // Print page text
        // ------------------------------

        String pageText = driver
                .findElement(By.tagName("body"))
                .getText();

        System.out.println(
                "========== PAGE TEXT =========="
        );

        System.out.println(pageText);

        System.out.println(
                "========== END PAGE TEXT =========="
        );

        // ------------------------------
        // Check Login
        // ------------------------------

        if (actURL.contains("/dashboard")) {

            System.out.println(
                    "Login successful"
            );

            // ------------------------------
            // Dismiss Chrome popup
            // ------------------------------

            Thread.sleep(1500);

            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);

            System.out.println(
                    "Chrome password/security popup dismissed"
            );

        } else {

            System.out.println(
                    "Login failed"
            );

            System.out.println(
                    "Current URL = " + actURL
            );

            Assert.fail(
                    "Login did not navigate to Dashboard. "
                    + "Current URL = " + actURL
            );
        }
    }

    // ==============================
    // LOGOUT
    // ==============================

    /*
    @Test(
            priority = 2,
            dependsOnMethods = "testLogin"
    )
    public void testLogout() {

        // Wait for Logout button
        WebElement logoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(logout_xpath)
                )
        );

        // Click Logout
        logoutButton.click();

        // Wait for Login page
        wait.until(
                ExpectedConditions.urlContains("/login")
        );

        // Get Current URL
        String actURL = driver.getCurrentUrl();

        System.out.println(
                "Current URL after Logout: "
                + actURL
        );

        // Verify Logout
        Assert.assertTrue(
                actURL.contains("/login"),
                "Logout failed. Current URL is: "
                + actURL
        );

        System.out.println(
                "Logout Test Passed"
        );
    }
    */

    // ==============================
    // TEARDOWN
    // ==============================

    @AfterTest
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
