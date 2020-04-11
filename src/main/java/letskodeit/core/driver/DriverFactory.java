package letskodeit.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
  private static final Logger LOGGER = LogManager.getLogger(DriverFactory.class);

  private WebDriver driver;
  private Dimension resolution = new Dimension(1440, 900);
  private String browserType;

  public WebDriver getDriver() {
    if (Objects.isNull(driver)) {
      browserType = System.getProperty("browser.type") == null ? "chrome"
          : System.getProperty("browser.type").toLowerCase();
      switch (browserType) {
        case "chrome":
          LOGGER.info("Starting Chrome driver");
          setupChromeDriver();
          break;
        case "firefox":
          setupFirefoxDriver();
          break;
        default:
          LOGGER.warn(String.format("Starting default (%s) driver", browserType));
          setupChromeDriver();
          break;
      }
      /* Docker issues: 'driver.manage().window().maximize()' generates runtime error */
      setupDriverAdditionalParameters();
      driver.manage().window().setSize(resolution);
      LOGGER.info("Started {} driver", browserType);
    }
    return driver;
  }

  private void setupFirefoxDriver() {
    LOGGER.info("Starting Firefox driver");
    WebDriverManager.getInstance(FirefoxDriver.class).setup();
    driver = FirefoxBrowser.setupDriver();
  }

  private void setupDriverAdditionalParameters() {
    driver.manage().deleteAllCookies();
    driver.manage().window().setPosition(new Point(0, 0));
    driver.manage().timeouts().implicitlyWait(DriverConfig.MAX_OBJECT_TIMEOUT,
        TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(DriverConfig.MAX_PAGE_LOAD_TIME,
        TimeUnit.SECONDS);
  }

  public WebDriver getMobileChromeDriver(final String mobileName) {
    if (Objects.isNull(driver)) {
      LOGGER.info("Starting Mobile webdriver");
      setupMobileChromeDriver(mobileName);
      setupDriverAdditionalParameters();
      LOGGER.info("Started Mobile driver");
    }
    return driver;
  }

  private void setupChromeDriver() {
    WebDriverManager.getInstance(ChromeDriver.class).setup();
    driver = ChromeBrowser.setupDriver();
  }

  private void setupMobileChromeDriver(final String mobileName) {
    WebDriverManager.getInstance(ChromeDriver.class).setup();
    driver = ChromeBrowser.setupMobileDriver(mobileName);
  }

  @PreDestroy
  public void closeDriver() {
    if (driver != null) {
      driver.close();
      try {
        driver.quit();
      } catch (final NoSuchSessionException e) {
        if (browserType.equalsIgnoreCase("firefox")) {
          LOGGER
              .warn("Probably Firefox https://bugzilla.mozilla.org/show_bug.cgi?id=1403510 error");
        } else {
          LOGGER.error(e.getMessage());
        }

      }
      LOGGER.info("Closed {} driver", browserType);
      driver = null;
    }
  }

  public boolean isDriverInitialised() {
    return driver != null;
  }

  public void changeScreenResolution(final int width, final int height) {
    resolution = new Dimension(width, height);
  }
}
