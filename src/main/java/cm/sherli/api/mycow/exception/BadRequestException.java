package cm.sherli.api.mycow.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

	  private static final long serialVersionUID = 1L;
  public BadRequestException(String message, HttpStatus badRequest) {
      super(message);
  }

}