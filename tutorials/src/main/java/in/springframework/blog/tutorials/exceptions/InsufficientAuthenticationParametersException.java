package in.springframework.blog.tutorials.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InsufficientAuthenticationParametersException extends BaseException {

    /**
     * Instantiates a new Entity already exists exception.
     */
    public InsufficientAuthenticationParametersException(){
        super();
    }

    /**
     * Instantiates a new Entity already exists exception.
     *
     * @param message the message
     */
    public InsufficientAuthenticationParametersException(String message){
        super(message);
    }

}
