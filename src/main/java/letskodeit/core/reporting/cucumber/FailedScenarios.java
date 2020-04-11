package letskodeit.core.reporting.cucumber;

import com.ipfdigital.core.exceptions.RerunFailedScenariosException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FailedScenarios {
  private static final Logger LOGGER = LogManager.getLogger(FailedScenarios.class);

  private static final String JSON_FILES_PATH = "target/cucumber-parallel/rerun";
  private static final String RERUN_FILE_NAME = "rerunTags.txt";

  private static void handleError(final String eMessage) {
    LOGGER.error(eMessage);
    throw new RerunFailedScenariosException(eMessage);
  }

  private static String prepareFailedScenarios(final File[] files) throws IOException {
    final StringBuilder builder = new StringBuilder();

    for (final File file : files) {
      try (final BufferedReader br = new BufferedReader(
          new FileReader(file.getAbsoluteFile()))) {
        String line = br.readLine();

        while (line != null) {
          builder.append(line);
          builder.append(" ");
          line = br.readLine();
        }
      }
    }

    return builder.toString();
  }

  public static void main(final String[] args) throws IOException {
    final File folder = new File(JSON_FILES_PATH);
    final File[] files = folder.listFiles();

    if (files == null) {
      final String eMessage = "Failed scenarios folder does not exist: " + JSON_FILES_PATH;
      handleError(eMessage);

    } else if (files.length == 0) {
      final String eMessage = "All scenarios passed: no failed tests were found";
      handleError(eMessage);

    } else {
      final String failedScenarios = prepareFailedScenarios(files);

      final File filePath = new File(JSON_FILES_PATH, RERUN_FILE_NAME);
      try (final BufferedWriter writer = new BufferedWriter(
          new FileWriter(filePath))) {
        writer.write(failedScenarios);
      }
    }
  }
}
