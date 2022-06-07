package cm.sherli.api.mycow.exception;

import org.springframework.http.HttpStatus;

/*
 * 
 */
public class ResourceAlreadyExists extends RuntimeException {

	  private static final long serialVersionUID = 1L;
    public ResourceAlreadyExists(String message, HttpStatus badRequest) {
        super(message);
    }
}