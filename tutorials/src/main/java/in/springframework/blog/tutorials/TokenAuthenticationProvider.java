package in.springframework.blog.tutorials;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final static Logger logger = Logger.getLogger(TokenAuthenticationProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenPrincipal principal = (TokenPrincipal) authentication.getPrincipal();
        try {

            Request request = new Request.Builder()
                    .url(String.format(requestUrl, principal.getToken().get()))
                    .post(RequestBody.create(MediaType.get("application/x-www-form-urlencoded"), ""))
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                OAuthResponse oAuthResponse = gson.fromJson(response.body().string(), OAuthResponse.class);
                principal.setName(oAuthResponse.user_name);
                List<H2ORole> authorityList = new ArrayList<>();
                oAuthResponse.authorities.stream().forEach(e -> authorityList.add(new H2ORole(e)));
                return new PreAuthenticatedAuthenticationToken(principal, principal.getToken(), authorityList);
            }
            else {

                throw new BadCredentialsException("Invalid token");
            }
        }
        catch(IOException ex) {
            throw new BadCredentialsException("Communication failure with OAuth Server", ex);
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
