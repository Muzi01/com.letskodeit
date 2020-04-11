package letskodeit.core.driver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestClass {



    @BeforeMethod
    public void beforeTest() throws InterruptedException {

        DriverProvider.getInstance().getDriver().get("https://www.bhphotovideo.com/");
        DriverProvider.getInstance().getDriver().manage().window().maximize();
        DriverProvider.getInstance().getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }


    @Test
    public void testOne() throws InterruptedException {
        System.out.println(Thread.currentThread().getId());
        DriverProvider.getInstance().getDriver().findElement(By.id("top-search-input")).sendKeys("pen drive");
        DriverProvider.getInstance().getDriver().findElement(By.xpath("//button[@name='Top Nav-Search']")).click();
        DriverProvider.getInstance().getDriver().findElement(By.linkText("SanDisk 128GB Ultra Flair USB 3.0 Flash Drive")).click();
        Thread.sleep(2000);
    }

    @Test
    public void testTwo() throws InterruptedException {
        System.out.println(Thread.currentThread().getId());
        DriverProvider.getInstance().getDriver().findElement(By.id("top-search-input")).sendKeys("dell laptop");
        DriverProvider.getInstance().getDriver().findElement(By.xpath("//button[@name='Top Nav-Search']")).click();
        DriverProvider.getInstance().getDriver().findElement(By.linkText("Dell 15.6\" Inspiron 15 5000 Series 5584 Multi-Touch Laptop")).click();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void afterTest() {
        DriverProvider.getInstance().removeDriver();

    }
}