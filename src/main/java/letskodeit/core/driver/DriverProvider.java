package letskodeit.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverProvider {

    private DriverProvider() {

    }

    private static DriverProvider instance = new DriverProvider();

    public static DriverProvider getInstance() {
        return instance;
    }


    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() {
        @Override
        protected WebDriver initialValue() {
            String chromePath = System.getProperty("user.dir")+"/chrome/chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", chromePath);
            return new ChromeDriver();

        }
    };


    public WebDriver getDriver() {
        return driver.get();
    }

    public void removeDriver() {
        driver.get().quit();
        driver.remove();
    }
}
