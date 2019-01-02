package in.springframework.blog.tutorials;

public final class BadRequestException extends BaseException {

    /**
     * The Code.
     */
    Integer code;

    /**
     * Instantiates a new Bad request exception.
     *
     * @param message the message
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param message the message
     * @param code    the code
     */
    public BadRequestException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public Integer getCode() {
        return code;
    }


}