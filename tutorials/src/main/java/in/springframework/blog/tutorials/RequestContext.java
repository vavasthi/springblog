package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.user.domain.User;

public class RequestContext {

    public static final ThreadLocal<User> currentUser = new ThreadLocal();
}
