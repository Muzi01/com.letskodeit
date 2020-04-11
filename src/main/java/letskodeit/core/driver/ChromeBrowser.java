package letskodeit.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ChromeBrowser {
  private static final Logger LOGGER = LogManager.getLogger(ChromeBrowser.class);
  private static final List<String> HEADLESS_ARGUMENT_LIST =
      Arrays.asList("--no-sandbox", "--headless", "--disable-gpu");
  private static final List<String> NO_HEADLESS_ARGUMENT_LIST =
      Arrays.asList("--disable-infobars", "--disable-notifications");

  private ChromeBrowser() {
    throw new IllegalStateException("This class should not be instantiated!");
  }


  private static ChromeOptions getOptions() {
    final ChromeOptions options = new ChromeOptions();

    final boolean headlessBrowserValue = provideHeadlessProperty();
    if (headlessBrowserValue) {
      HEADLESS_ARGUMENT_LIST.forEach(options::addArguments);
    } else {
      NO_HEADLESS_ARGUMENT_LIST.forEach(options::addArguments);
    }

    return options;
  }

  private static boolean provideHeadlessProperty() {
    final String headlessFlag = System.getProperty("headless");
    if (StringUtils.isBlank(headlessFlag)) {
      return false;
    }

    return Boolean.parseBoolean(headlessFlag);
  }

  static WebDriver setupDriver() {
    final ChromeOptions options = ChromeBrowser.getOptions();
    LOGGER.info(
        "Chrome webdriver version: {}", WebDriverManager.chromedriver().getDownloadedVersion());

    return new ChromeDriver(options);
  }

  static WebDriver setupMobileDriver(final String mobileName) {
    final ChromeOptions options = ChromeBrowser.getOptions();
    final Map<String, String> mobileEmulation = new HashMap<>();

    mobileEmulation.put("deviceName", MobileTypes.fromString(mobileName));
    options.setExperimentalOption("mobileEmulation", mobileEmulation);
    LOGGER.info("Chrome webdriver version for Mobile tests: "
        + WebDriverManager.chromedriver().getDownloadedVersion());

    return new ChromeDriver(options);
  }
}
