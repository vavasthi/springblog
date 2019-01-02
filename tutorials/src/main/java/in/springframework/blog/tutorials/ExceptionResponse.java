package in.springframework.blog.tutorials;

public class ExceptionResponse extends AbstractResponse {

    /**
     * Instantiates a new Exception response.
     */
    public ExceptionResponse() {

    }
    // Since data is not required here , we are returning null

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setData(Object data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
