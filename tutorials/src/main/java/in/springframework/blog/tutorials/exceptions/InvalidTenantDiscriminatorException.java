package in.springframework.blog.tutorials.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidTenantDiscriminatorException extends BaseException {

    /**
     * Instantiates a new Entity already exists exception.
     */
    public InvalidTenantDiscriminatorException(){
        super();
    }

    /**
     * Instantiates a new Entity already exists exception.
     *
     * @param message the message
     */
    public InvalidTenantDiscriminatorException(String message){
        super(message);
    }

}
