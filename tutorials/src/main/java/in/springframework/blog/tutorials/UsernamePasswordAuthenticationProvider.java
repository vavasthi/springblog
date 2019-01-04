package in.springframework.blog.tutorials;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final static Logger logger = Logger.getLogger(UsernamePasswordAuthenticationProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordPrincipal principal = (UsernamePasswordPrincipal) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findUserByEmailOrUsername(principal.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (PasswordManager.INSTANCE.matches(principal.getPassword(), user.getPassword())) {
                user = userRepository.findById(user.getId()).get();
                user.setAuthToken(UUID.randomUUID().toString() + "-" + UUID.randomUUID());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.HOUR, 24);
                user.setExpiry(calendar.getTime());
                RequestContext.currentUser.set(user);
                return new UsernamePasswordAuthenticationToken(principal, null, UserAuthority.getAuthoritiesFromRoles(user.getMask()));
            }
            else {

                throw new BadCredentialsException(String.format("User %s doesn't exist.", principal.getName()));
            }
        }
        else {

            throw new BadCredentialsException(String.format("User %s doesn't exist.", principal.getName()));
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
