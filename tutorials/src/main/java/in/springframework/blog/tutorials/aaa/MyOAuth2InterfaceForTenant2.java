package in.springframework.blog.tutorials.aaa;

import in.springframework.blog.tutorials.exceptions.AuthenticationFailedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyOAuth2InterfaceForTenant2 implements AAAInterface{
    @Override
    public List<String> authenticate(String token) throws AuthenticationFailedException {
        return null;
    }
}
