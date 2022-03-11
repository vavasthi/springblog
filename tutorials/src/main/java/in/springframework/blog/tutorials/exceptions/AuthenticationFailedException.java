package in.springframework.blog.tutorials.exceptions;

import in.springframework.blog.tutorials.utils.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by vavasthi on 28/01/16.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthenticationFailedException extends BaseException {

    /**
     * Instantiates a new Not found exception.
     *
     * @param errorCode the error code
     * @param Message   the message
     */
    public AuthenticationFailedException(int errorCode, String Message) {

        super(errorCode, ErrorCodes.AUTHENTICATION_FAILED+Message);
    }

    /**
     * Instantiates a new Not found exception.
     *
     * @param message the message
     */
    public AuthenticationFailedException(String message) {
        super(message);
    }

}