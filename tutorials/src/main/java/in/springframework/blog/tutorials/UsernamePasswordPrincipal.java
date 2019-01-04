package in.springframework.blog.tutorials;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by vinay on 2/3/16.
 */
public class UsernamePasswordPrincipal implements Principal {


    public UsernamePasswordPrincipal(Optional<String> username, Optional<String> password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getName() {
        return username.get();
    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<String> getPassword() {
        return password;
    }

    private final Optional<String> username;
    private final Optional<String> password;
}