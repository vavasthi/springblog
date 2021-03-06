package in.springframework.blog.tutorials.principals;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by vinay on 2/3/16.
 */
public class UsernamePasswordPrincipal implements Principal {


    public UsernamePasswordPrincipal(Optional<String> username) {
        this.username = username;
        this.password = Optional.empty();
        this.newUser = true;
    }
  public UsernamePasswordPrincipal(Optional<String> username, Optional<String> password) {
    this.username = username;
    this.password = password;
    this.newUser = false;
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

  public boolean isNewUser() {
    return newUser;
  }

  private final Optional<String> username;
    private final Optional<String> password;
    private final boolean newUser;
}