package letskodeit.core.exceptions;

public class RerunFailedScenariosException extends RuntimeException {
  private static final long serialVersionUID = -4431018640538183778L;

  public RerunFailedScenariosException(final String message) {
    super(message);
  }
}
