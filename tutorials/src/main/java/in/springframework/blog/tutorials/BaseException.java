package in.springframework.blog.tutorials;

/**
 * Created by vinay on 1/11/16.
 */
public class BaseException extends RuntimeException {

    private int errorCode;
    private boolean shouldLog = true;

    /**
     * Instantiates a new Hubble base exception.
     */
    public BaseException() {
        super("");
    }

    /**
     * Instantiates a new Hubble base exception.
     *
     * @param errorCode the error code
     * @param message   the message
     */
    public BaseException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new Hubble base exception.
     *
     * @param message the message
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Hubble base exception.
     *
     * @param cause the cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Hubble base exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Hubble base exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public BaseException(String message,
                         Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean shouldLog() {
        return shouldLog;
    }
    public void shouldLog(boolean shouldLog) {
        this.shouldLog = shouldLog;
    }
}
