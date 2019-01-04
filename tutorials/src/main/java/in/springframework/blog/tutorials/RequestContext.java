package in.springframework.blog.tutorials;

public class RequestContext {

    public static final ThreadLocal<User> currentUser = new ThreadLocal();
}
