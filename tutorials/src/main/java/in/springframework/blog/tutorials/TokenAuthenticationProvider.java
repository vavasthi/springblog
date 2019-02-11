package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.user.domain.User;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.*;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final static Logger logger = Logger.getLogger(TokenAuthenticationProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenPrincipal principal = (TokenPrincipal) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findUserByAuthToken(principal.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getExpiry() != null && user.getExpiry().after(new Date())) {
                RequestContext.currentUser.set(user);
                return new PreAuthenticatedAuthenticationToken(principal, null, UserAuthority.getAuthoritiesFromRoles(user.getMask()));
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
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
