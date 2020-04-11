package letskodeit.core.driver;

public class DriverConfig {
  public static final int MAX_PAGE_LOAD_TIME = 90; // Seconds
  public static final int MAX_OBJECT_TIMEOUT = 15; // Seconds
  public static final int PAGE_SLEEP_DURATION = 1000; // ms
  public static final int POLLING_TIME = 5;

  private DriverConfig() {
    throw new IllegalStateException("This class should not be instantiated!");
  }
}
