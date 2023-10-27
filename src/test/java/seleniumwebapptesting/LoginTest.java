package seleniumwebapptesting;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void Seleniumtest1() {
        System.out.println("In test 1");
        driver.get("http://google.com");
        String expectedPageTitle = "Google";
        Assert.assertTrue("Test Failed", driver.getTitle().contains(expectedPageTitle));
    }

    @Test
    public void Seleniumtest2() {
        System.out.println("In test 2");
        driver.get("https://www.browserstack.com/users/sign_in");
        WebElement username=driver.findElement(By.id("user[login"));
        WebElement password=driver.findElement(By.id("user_password"));
        WebElement login=driver.findElement(By.name("commit"));
        username.sendKeys("abc@gmail.com");
        password.sendKeys("your_password");
        login.click();
        String actualUrl="https://live.browserstack.com/dashboard";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }

    @Test
    public void Seleniumtest3() {
        System.out.println("In test 3");
    }

    @AfterClass
    public static void tearDown() {
        if(driver!=null)
            driver.quit();
    }
}


// References: https://www.browserstack.com/guide/selenium-webdriver-tutorial