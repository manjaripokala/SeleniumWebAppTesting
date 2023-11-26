package seleniumwebapptesting.safari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import java.util.List;

public class ProductListingSafariTest {

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
    public void verifyProductListDisplayed() {
        // Open the URL
        driver.get(baseUrl);

        // Verify that products are displayed in the "Shop New Yoga" collection
        List<WebElement> productList = driver.findElements(By.className("product-item"));
        assert productList.size() > 0 : "No products are displayed in the list.";
    }

    @Test
    public void verifyProductDetailsCanBeViewed() {
        // Open the URL
        driver.get(baseUrl);

        // Click on the first product in the list
        WebElement firstProduct = driver.findElement(By.cssSelector(".product-item:first-child"));
        firstProduct.click();

        // Verify that the product details are displayed
        WebElement productName = driver.findElement(By.className("product-info-main"));
        WebElement productPrice = driver.findElement(By.className("price"));

        assert productName.isDisplayed() : "Product name is not displayed.";
        assert productPrice.isDisplayed() : "Product price is not displayed.";
    }

//    @Test
//    public void verifyAddToCart() {
//        // Open the URL
//        driver.get(baseUrl);
//
//        // Click on the first product in the list
//        WebElement firstProduct = driver.findElement(By.cssSelector(".product-item:first-child"));
//        firstProduct.click();
//
//        // Click on the "Add to Cart" button for the first product
//        //WebElement addToCartButton = driver.findElement(By.cssSelector(".product-item:first-child .action.tocart"));
//        WebElement addToCartButton = driver.findElement(By.className("tocart"));
//        addToCartButton.click();
//
//        // Verify that the product is added to the cart
//        WebElement cartItemCount = driver.findElement(By.cssSelector(".minicart-wrapper .counter-number"));
//        assert Integer.parseInt(cartItemCount.getText()) > 0 : "Product was not added to the cart.";
//    }

    @Test
    public void verifyProductDescription() {
        // Open the URL
        driver.get(baseUrl);

        // Click on the first product in the list
        WebElement firstProduct = driver.findElement(By.cssSelector(".product-item:first-child"));
        firstProduct.click();

        // Click on the "Description" tab
        WebElement descriptionTab = driver.findElement(By.cssSelector(".data.item.title"));
        descriptionTab.click();

        // Verify that the product description is displayed
        WebElement productDescription = driver.findElement(By.cssSelector(".product.attribute.description"));
        assert productDescription.isDisplayed() : "Product description is not displayed.";
    }

    @After
    public void tearDown() {
        // Close the browser after each test
        driver.quit();
    }
}

