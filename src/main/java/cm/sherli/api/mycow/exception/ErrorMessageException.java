package cm.sherli.api.mycow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorMessageException extends RuntimeException {

	  private static final long serialVersionUID = 1L;

	  public ErrorMessageException(String msg) {super(msg);}
	  public ErrorMessageException(Throwable cause,String msg) {super(msg,cause);}
	}