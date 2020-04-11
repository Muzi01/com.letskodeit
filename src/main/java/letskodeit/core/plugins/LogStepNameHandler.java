package letskodeit.core.plugins;

import cucumber.api.HookTestStep;
import cucumber.api.PickleStepTestStep;
import cucumber.api.Result;
import cucumber.api.TestStep;
import cucumber.api.event.EventHandler;
import cucumber.api.event.TestStepFinished;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogStepNameHandler implements EventHandler<TestStepFinished> {

  private static final Logger log = LogManager.getLogger(LogStepNameHandler.class);
  private static final String UNIX_SEPARATOR = "/";
  private static final String PASSED_MSG = "Successfully executed step name: \'%s\' from file %s";
  private static final String FAILED_MSG = "Step \'%s\' from file %s failed";

  @Override
  public void receive(final TestStepFinished testStepFinished) {
    final boolean isIncorrectStepName = testStepFinished.testStep instanceof HookTestStep;
    if (!isIncorrectStepName) {
      final TestStep testStep = testStepFinished.testStep;
      final PickleStepTestStep pickleStepTestStep = (PickleStepTestStep) testStep;
      if (testStepFinished.result.is(Result.Type.PASSED)) {
        log.info(getLogMsg(PASSED_MSG, testStep, pickleStepTestStep));
      } else if (testStepFinished.result.is(Result.Type.FAILED)) {
        // testStepFinished.result.getError() - stacktrace from test execution. Add to Jira in
        // future
        log.error(getLogMsg(FAILED_MSG, testStep, pickleStepTestStep));
      }
    }
  }

  private String getLogMsg(final String msg, final TestStep testStep,
      final PickleStepTestStep pickleStepTestStep) {
    return String.format(msg, pickleStepTestStep.getStepText(), getName(testStep));
  }

  private String getName(final TestStep testStep) {
    final String stepLocation = testStep.getCodeLocation();
    final int startIndex = stepLocation.lastIndexOf(UNIX_SEPARATOR) + 1;
    return stepLocation.substring(startIndex);
  }
}
