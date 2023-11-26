package seleniumwebapptesting.safari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SortAndFilterSafariTest {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        // Initialize SafariDriver and navigate to the base URL
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();

        baseUrl = "https://magento.softwaretestingboard.com/collections/yoga-new.html";
    }
    @Test
    public void testSortingByPrice() {
        // Scenario: Verify that sorting by price displays products in correct order

        // Open the collection page
        driver.get(baseUrl);

        // Click on the 'Sort by' dropdown and select 'Price'
        WebElement sortByDropdown = driver.findElement(By.cssSelector(".toolbar-sorter select"));
        sortByDropdown.sendKeys("Price");

        // Wait for the page to refresh and products to rearrange
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".price-box .price")));

        // Get the list of product prices
        List<WebElement> productPrices = driver.findElements(By.cssSelector(".price-box .price"));

        // Verify if prices are in ascending order
        for (int i = 0; i < productPrices.size() - 1; i++) {
            double price1 = Double.parseDouble(productPrices.get(i).getText().replace("$", ""));
            double price2 = Double.parseDouble(productPrices.get(i + 1).getText().replace("$", ""));
            Assert.assertTrue("Prices are not in ascending order", price1 <= price2);
        }
    }

    @Test
    public void testSortingByName() {
        // Scenario: Verify that sorting by product name displays products in correct order

        // Open the collection page
        driver.get(baseUrl);

        // Click on the 'Sort by' dropdown and select 'Product Name'
        WebElement sortByDropdown = driver.findElement(By.cssSelector(".toolbar-sorter select"));
        sortByDropdown.sendKeys("Product Name");

        // Get the list of product names
        List<WebElement> productNames = driver.findElements(By.cssSelector("product-item-name"));

        // Verify if names are in alphabetical order
        for (int i = 0; i < productNames.size() - 1; i++) {
            String name1 = productNames.get(i).getText();
            String name2 = productNames.get(i + 1).getText();
            Assert.assertTrue("Names are not in alphabetical order", name1.compareTo(name2) <= 0);
        }
    }

    @Test
    public void testFilteringBySize() {
        // Scenario: Verify that filtering by size displays products with only selected size

        // Open the collection page
        driver.get(baseUrl);

        // Find the filter element 'Size' and click to expand
        String filterAttribute = "Size";
        WebElement filterElement = driver.findElement(By.xpath("//div[contains(text(), '" + filterAttribute + "')]"));
        filterElement.click();

        // Find and click the desired size option - "XS"
        String optionSize = "XS";
        WebElement optionElement = filterElement.findElement(By.xpath("//div[contains(text(), '" + optionSize + "')]"));
        optionElement.click();

        // Get the list of filtered products
        List<WebElement> filteredProducts = driver.findElements(By.cssSelector(".product-item-details"));

        // Verify if all filtered products have the selected size
        for (WebElement product : filteredProducts) {
            // Find the element by its option-label attribute
            WebElement productSizeElement = product.findElement(By.xpath("//div[@option-label='" + optionSize + "']"));

            // Check if the option is selected
            boolean isOptionSelected = productSizeElement.getAttribute("aria-checked").equals("true");
            Assert.assertEquals("Filtered product does not have the selected size", true, isOptionSelected);
        }
    }

    @Test
    public void testFilteringByColor() {
        // Scenario: Verify that filtering by color displays products with only selected color

        // Open the collection page
        driver.get(baseUrl);

        // Find the filter element 'Color' and click to expand
        String filterAttribute = "Color";
        WebElement filterElement = driver.findElement(By.xpath("//div[contains(text(), '" + filterAttribute + "')]"));
        filterElement.click();

        // Find and click the desired option - "Blue"
        String optionColor = "Blue";
        String xpathExpression = "//div[@option-label='" + optionColor + "']";
        WebElement optionElement = filterElement.findElement(By.xpath(xpathExpression));
        optionElement.click();

        // Get the list of filtered products
        List<WebElement> filteredProducts = driver.findElements(By.cssSelector(".product-item"));

        // Verify if all filtered products have the selected color
        for (WebElement product : filteredProducts) {
            // Find the element by its option-label attribute
            WebElement productColorElement = product.findElement(By.xpath(xpathExpression));

            // Check if the option is selected
            boolean isOptionSelected = productColorElement.getAttribute("aria-checked").equals("true");
            Assert.assertEquals("Filtered product does not have the selected color", true, isOptionSelected);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
