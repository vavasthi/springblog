package in.springframework.blog.tutorials.pojos;

public class MyTopicMessage {
    public MyTopicMessage() {
    }

    public MyTopicMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
