package in.springframework.blog.tutorials;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by vinay on 2/3/16.
 */
public class TokenPrincipal implements Principal {


    public TokenPrincipal(Optional<String> token) {
        this.name = (token.isPresent() ? token.get() : "None");
        this.token = token;
    }

    public Optional<String> getToken() {
        return token;
    }

    public void setToken(Optional<String> token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    private String name;
    private Optional<String> token;
}