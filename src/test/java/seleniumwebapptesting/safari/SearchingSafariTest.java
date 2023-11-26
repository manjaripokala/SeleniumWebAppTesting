package seleniumwebapptesting.safari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchingSafariTest {

    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        // Initialize SafariDriver and navigate to the base URL
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();

        baseUrl = "https://magento.softwaretestingboard.com/collections/yoga-new.html";
        driver.get(baseUrl);
    }
    @Test
    public void testSearchBarFunctionality() {
        // Scenario: Verify that the search bar is functional.

        // Find the search bar element
        WebElement searchBar = driver.findElement(By.id("search"));

        // Enter a search term
        searchBar.sendKeys("Yoga Mat");

        // Find and click the search button
        WebElement searchButton = driver.findElement(By.cssSelector("button[title='Search']"));
        searchButton.click();

        // Verify that search results are displayed
        WebElement searchResults = driver.findElement(By.className("catalogsearch-result-index"));
        assertTrue(searchResults.isDisplayed());
    }

    @Test
    public void testValidSearchResults() {
        // Scenario: Confirm that relevant search results are displayed for valid search terms.

        // Find the search bar element
        WebElement searchBar = driver.findElement(By.id("search"));

        // Enter a valid search term
        searchBar.sendKeys("Pants");

        // Find and click the search button
        WebElement searchButton = driver.findElement(By.cssSelector("button[title='Search']"));
        searchButton.click();

        // Verify that the search results contain the expected product
        WebElement productResult = driver.findElement(By.className("product-item-link"));
        assertTrue(productResult.getText().contains("Pant"));
    }

    @Test
    public void testInvalidSearchResults() {
        // Scenario: Ensure that an appropriate message is shown for invalid or no search results.

        // Find the search bar element
        WebElement searchBar = driver.findElement(By.id("search"));

        // Enter an invalid search term
        searchBar.sendKeys("tugkhi");

        // Find and click the search button
        WebElement searchButton = driver.findElement(By.cssSelector("button[title='Search']"));
        searchButton.click();

        // Verify that the "Your search returned no results" message is displayed
        WebElement noResultsMessage = driver.findElement(By.cssSelector(".message.notice"));
        assertEquals("Your search returned no results.", noResultsMessage.getText());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


