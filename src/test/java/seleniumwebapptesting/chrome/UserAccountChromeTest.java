package seleniumwebapptesting.chrome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserAccountChromeTest {

    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        // Initialize ChromeDriver and navigate to the base URL
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        baseUrl = "https://magento.softwaretestingboard.com/";
        driver.get(baseUrl);
    }

    @Test
    public void testUserRegistrationSuccess() {
        // Click on the registration link or navigate to the registration page
        WebElement registerLink = driver.findElement(By.linkText("Create an Account"));
        registerLink.click();

        // Fill in registration form details
        WebElement firstNameField = driver.findElement(By.id("firstname"));
        firstNameField.sendKeys("John");

        WebElement lastNameField = driver.findElement(By.id("lastname"));
        lastNameField.sendKeys("Doe");

        WebElement emailField = driver.findElement(By.id("email_address"));
        emailField.sendKeys("john108.doe108@example.com");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");

        WebElement confirmPasswordField = driver.findElement(By.id("password-confirmation"));
        confirmPasswordField.sendKeys("Password123");

        // Submit the registration form
        WebElement registerButton = driver.findElement(By.cssSelector(".action.submit.primary"));
        registerButton.click();

        // Check for a welcome message or navigate to the user account page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement welcomeMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'message-success') and contains(@class, 'success')]")));
        assertTrue(welcomeMessage.isDisplayed());

        // Verify that the user account page is displayed
        assertEquals("https://magento.softwaretestingboard.com/customer/account/", driver.getCurrentUrl());
    }

    @Test
    public void testUserRegistrationFail() {
        // Click on the registration link or navigate to the registration page
        WebElement registerLink = driver.findElement(By.linkText("Create an Account"));
        registerLink.click();

        // Fill in registration form details
        WebElement firstNameField = driver.findElement(By.id("firstname"));
        firstNameField.sendKeys("John");

        WebElement lastNameField = driver.findElement(By.id("lastname"));
        lastNameField.sendKeys("Doe");

        WebElement emailField = driver.findElement(By.id("email_address"));
        emailField.sendKeys("john.doe@example.com");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");

        WebElement confirmPasswordField = driver.findElement(By.id("password-confirmation"));
        confirmPasswordField.sendKeys("Password123");

        // Submit the registration form
        WebElement registerButton = driver.findElement(By.cssSelector(".action.submit.primary"));
        registerButton.click();

        // Check for a welcome message or navigate to the user account page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'message-error')]")));
        assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void testUserLoginSuccess() {
        // Click on the login link or navigate to the login page
        WebElement loginLink = driver.findElement(By.linkText("Sign In"));
        loginLink.click();

        // Fill in login form details
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("john108.doe108@example.com");

        WebElement passwordField = driver.findElement(By.id("pass"));
        passwordField.sendKeys("Password123");

        // Submit the login form
        WebElement loginButton = driver.findElement(By.cssSelector("#send2"));
        loginButton.click();

        // Check for a welcome message or navigate to the user account page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement welcomeMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(@class, 'logged-in') and contains(text(), 'Welcome')]")));
        //WebElement welcomeMessage = driver.findElement(By.xpath("//span[contains(@class, 'logged-in') and contains(text(), 'Welcome')]"));

//        assertTrue(welcomeMessage.isDisplayed());
//        WebElement welcomeMessage = driver.findElement(By.className("logged-in"));
        System.out.println(welcomeMessage.getText());
        assertTrue(welcomeMessage.isDisplayed());
        assertTrue(welcomeMessage.getText().contains("John Doe"));

        // Verify that the user account page is displayed
        assertEquals("https://magento.softwaretestingboard.com/", driver.getCurrentUrl());
    }

    @Test
    public void testUserLoginFail() {
        // Click on the login link or navigate to the login page
        WebElement loginLink = driver.findElement(By.linkText("Sign In"));
        loginLink.click();

        // Fill in login form details
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("john.doe@example.com");

        WebElement passwordField = driver.findElement(By.id("pass"));
        passwordField.sendKeys("Password123");

        // Submit the login form
        WebElement loginButton = driver.findElement(By.cssSelector("#send2"));
        loginButton.click();

        // Check for an error message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'message-error')]")));
        assertTrue(errorMessage.isDisplayed());
    }

    @After
    public void tearDown() {
        // Close the browser after each test
        driver.quit();
    }
}
