package seleniumwebapptesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NavigationTest {

    private static WebDriver driver;
    private static String homePageUrl;
    private static String navigatedPageUrl;

    @Before
    public void setUp() {
        // Initialize ChromeDriver and navigate to the base URL
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        homePageUrl = "https://magento.softwaretestingboard.com/";
        navigatedPageUrl = homePageUrl + "collections/yoga-new.html";
        driver.get(homePageUrl);
    }

    @Test
    public void test101_HomepageLogoVisibility() {
        // Verify that the homepage logo is visible
        WebElement logo = driver.findElement(By.cssSelector(".logo"));
        assertTrue(logo.isDisplayed());
    }

    @Test
    public void test102_YogaCollectionPageNavigation() {
        // Locate the "Shop New Yoga" button by class name
        WebElement shopNewYogaButton = driver.findElement(By.className("button"));

        // Perform actions on the button, e.g., click
        shopNewYogaButton.click();

        // Verify that the URL matches the expected Yoga collection URL
        assertEquals(navigatedPageUrl, driver.getCurrentUrl());
    }

    @Test
    public void test103_testHomepageNavigation() {
        //Go to 'Shop New Yoga' page
        driver.get(navigatedPageUrl);

        // Click on the homepage link
        WebElement homeLink = driver.findElement(By.className("home"));
        homeLink.click();

        // Verify that the URL matches the expected homepage URL
        assertEquals(homePageUrl, driver.getCurrentUrl());
    }

    @Test
    public void test104_YogaCollectionPageTitle() {
        // Locate the "Shop New Yoga" button by class name
        WebElement shopNewYogaButton = driver.findElement(By.className("button"));

        // Perform actions on the button, e.g., click
        shopNewYogaButton.click();

        // Verify that the title of the Yoga New collection page is correct
        String expectedTitle = "New Luma Yoga Collection";
        assertEquals(expectedTitle, driver.getTitle());
    }

    @After
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
