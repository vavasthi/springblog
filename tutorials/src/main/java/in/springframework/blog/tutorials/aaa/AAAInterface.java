package in.springframework.blog.tutorials.aaa;

import in.springframework.blog.tutorials.exceptions.AuthenticationFailedException;

import java.util.List;

public interface AAAInterface {
    List<String> authenticate(String token) throws AuthenticationFailedException;
}
