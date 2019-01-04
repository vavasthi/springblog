package in.springframework.blog.tutorials;

import java.util.Optional;

public class PasswordManager {
    public static final PasswordManager INSTANCE = new PasswordManager();
    public boolean matches(Optional<String> incomingPassword, String storedPassword) {
        String ip = (incomingPassword.isPresent() ? incomingPassword.get() : "");
        return ip.equals(storedPassword);
    }
}
