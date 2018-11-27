package in.springframework.blog.tutorials;

public class Hello {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String message = "Hello %s";
    private String name;
}
