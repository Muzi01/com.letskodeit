package letskodeit.core.plugins;

import cucumber.api.event.EventListener;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestStepFinished;

public class TestPlugin implements EventListener {
  @Override
  public void setEventPublisher(final EventPublisher eventPublisher) {
    eventPublisher.registerHandlerFor(TestStepFinished.class, new LogStepNameHandler());
  }
}
