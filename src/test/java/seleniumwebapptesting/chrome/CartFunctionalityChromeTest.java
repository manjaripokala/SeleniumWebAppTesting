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
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CartFunctionalityChromeTest {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        // Initialize ChromeDriver and navigate to the base URL
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        baseUrl = "https://magento.softwaretestingboard.com/collections/yoga-new.html";
        driver.get(baseUrl);
    }
    @Test
    public void testAddProductToCart() {
        // Click on the first product in the list
        WebElement firstProduct = driver.findElement(By.cssSelector(".product-item:first-child"));

        // Select size
        WebElement sizeOption = firstProduct.findElement(By.xpath("//div[@option-label='28']"));
        sizeOption.click();

        // Select color
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement colorOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@option-label='Blue']")));
        //WebElement colorOption = firstProduct.findElement(By.xpath("//div[@option-label='Blue']"));
        colorOption.click();

        // Click the 'Add to Cart' button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//button[@title='Add to Cart']")));
        WebElement addToCartButton = firstProduct.findElement(By.xpath(".//button[@title='Add to Cart']"));
        addToCartButton.click();

        // Wait for the cart count to update and 'loading' class is not present
        //wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Boolean cartElementLoaded = wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.cssSelector(".counter.qty"), "class", "loading")
        ));

        // Get the cart qty
        String cartCount = "";
        if (cartElementLoaded) {
            WebElement counterElement = driver.findElement(By.cssSelector(".counter-number"));
            cartCount = counterElement.getText();
        }

        assert cartCount.equals("1") : "Product was not added to the cart.";
    }



    @Test
    public void testRemoveProductFromCart() {
        // Click on the first product in the list
        WebElement firstProduct = driver.findElement(By.cssSelector(".product-item:first-child"));

        // Select size
        WebElement sizeOption = firstProduct.findElement(By.xpath("//div[@option-label='28']"));
        sizeOption.click();

        // Select color
        WebElement colorOption = firstProduct.findElement(By.xpath("//div[@option-label='Blue']"));
        colorOption.click();

        // Click the 'Add to Cart' button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//button[@title='Add to Cart']")));
        WebElement addToCartButton = firstProduct.findElement(By.xpath(".//button[@title='Add to Cart']"));
        addToCartButton.click();

        // Wait for the cart count to update and 'loading' class is not present
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.cssSelector(".counter.qty"), "class", "loading")
        ));

        //Click view cart
        WebElement viewCart = driver.findElement(By.cssSelector(".showcart"));
        viewCart.click();

        // Click the 'Remove' button in the cart
        WebElement deleteButton = driver.findElement(By.xpath("//a[@class='action delete']"));
        deleteButton.click();

        // Click the 'Delete OK' button in the dialog cart
        WebElement acceptDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action-accept")));
        acceptDeleteButton.click();

        // Get the cart qty
        wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.cssSelector(".counter.qty.empty"), "class", "loading")
        ));
        WebElement counterElement = driver.findElement(By.cssSelector(".counter-number"));
        String cartCount = counterElement.getText();

        assert cartCount.equals("") : "Product was not removed from the cart.";
    }

    @Test
    public void testCartTotalUpdate() {
        // Get the list of filtered products
        List<WebElement> filteredProducts = driver.findElements(By.cssSelector(".product-item"));

        // Select the product from the list
        WebElement product = filteredProducts.get(0);
        double price1 = Double.parseDouble(product.findElement(By.cssSelector(".price-box .price")).getText().replace("$", ""));
        System.out.println(price1);
        // Select size
        WebElement sizeOption = product.findElement(By.xpath("//div[@option-label='28']"));
        sizeOption.click();

        // Select color
        WebElement colorOption = product.findElement(By.xpath("//div[@option-label='Blue']"));
        colorOption.click();

        // Click the 'Add to Cart' button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//button[@title='Add to Cart']")));
        //WebElement addToCartButton = product.findElement(By.xpath(".//button[@title='Add to Cart']"));
        addToCartButton.click();

        // Click another 'Add to Cart' for quantity=2
        wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.cssSelector(".tocart"), "class", "disabled")
        ));
        addToCartButton.click();

        // Wait for the cart count to update and 'loading' class is not present
        wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.cssSelector(".counter.qty"), "class", "loading")
        ));

        //Click view cart
        WebElement viewCart = driver.findElement(By.cssSelector(".showcart"));
        viewCart.click();

        // Get the initial cart total
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='price']")));
        WebElement cartTotalElement = driver.findElement(By.xpath("//span[@class='price']"));
        double initialCartTotal = Double.parseDouble(cartTotalElement.getText().replace("$", ""));

        // Verify that the cart total is updated
        assert initialCartTotal == price1+ price1 : "Cart total was not updated.";
    }

    @Test
    public void testCheckoutProcess() {
        // Click on the first product in the list
        WebElement firstProduct = driver.findElement(By.cssSelector(".product-item:first-child"));

        // Select size
        WebElement sizeOption = firstProduct.findElement(By.xpath("//div[@option-label='28']"));
        sizeOption.click();

        // Select color
        WebElement colorOption = firstProduct.findElement(By.xpath("//div[@option-label='Blue']"));
        colorOption.click();

        // Click the 'Add to Cart' button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//button[@title='Add to Cart']")));
        WebElement addToCartButton = firstProduct.findElement(By.xpath(".//button[@title='Add to Cart']"));
        addToCartButton.click();

        // Wait for the cart count to update and 'loading' class is not present
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.cssSelector(".counter.qty"), "class", "loading")
        ));

        //Click view cart
        WebElement viewCart = driver.findElement(By.cssSelector(".showcart"));
        viewCart.click();

        // Click the 'Proceed to Checkout' button
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Proceed to Checkout']")));
        WebElement checkoutButton = driver.findElement(By.xpath("//button[@title='Proceed to Checkout']"));
        checkoutButton.click();

        // Verify that the checkout page is displayed
        assertEquals("https://magento.softwaretestingboard.com/checkout/", driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
