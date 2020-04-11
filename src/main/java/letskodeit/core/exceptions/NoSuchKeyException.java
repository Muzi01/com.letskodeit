package letskodeit.core.exceptions;

public class NoSuchKeyException extends RuntimeException {
  private static final long serialVersionUID = -2778867594728068217L;

  public NoSuchKeyException(final String message) {
    super(message);
  }
}
