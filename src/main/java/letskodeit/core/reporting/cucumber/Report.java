package letskodeit.core.reporting.cucumber;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Report {
  private static final String REPORT_DIRECTORY = "target";
  private static final String JSON_REPORT_DIRECTORY = "target/cucumber-parallel/json";

  private static final Logger LOG = LogManager.getLogger(Report.class);

  public static void main(final String[] args) {

    new Report().generate();
  }

  private void generate() {

    final File folder = new File(JSON_REPORT_DIRECTORY);
    if (!folder.exists()) {
      LOG.error("Report folder does not exist {}", JSON_REPORT_DIRECTORY);
      return;
    }

    final File[] files = folder.listFiles();
    if (files == null || files.length == 0) {
      LOG.error("No Cucumber reports were found at {}", JSON_REPORT_DIRECTORY);
      return;
    }

    LOG.info("Generating report, found {} json files", files.length);

    generate(files);
  }

  private void generate(final File[] files) {

    final List<String> jsonFilePaths = Arrays.stream(files)
        .filter(File::exists)
        .filter(f -> f.length() > 0)
        .map(File::getAbsolutePath)
        .collect(Collectors.toList());

    generate(jsonFilePaths);
  }

  private void generate(final List<String> jsonFilePaths) {

    new ReportBuilder(jsonFilePaths, prepareConfiguration()).generateReports();
  }

  private Configuration prepareConfiguration() {

    final Configuration configuration =
        new Configuration(new File(REPORT_DIRECTORY), "Acceptance tests");
    configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
    configuration.setBuildNumber("1");
    return configuration;
  }
}
