package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.user.domain.User;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.transaction.Transactional;
import java.util.*;

@Log4j2
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordPrincipal principal = (UsernamePasswordPrincipal) authentication.getPrincipal();
        if (principal.isNewUser()) {

            if (userRepository.findUserByEmailOrUsername(principal.getName()).isPresent()) {
                throw new EntityAlreadyExistsException(String.format("Username %s already exists.", principal.getName()));
            }
            List<Role> roles = Arrays.asList(Role.NEWUSER);
            return new UsernamePasswordAuthenticationToken(principal, null, UserAuthority.getAuthoritiesFromRoles(roles));
        }
        else {

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
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
