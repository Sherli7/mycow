package cm.sherli.api.mycow.exception;

public class ResourceNotAuthorized extends RuntimeException {

	  private static final long serialVersionUID = 1L;
  public ResourceNotAuthorized(String message) {
      super(message);
  }
}