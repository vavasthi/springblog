package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.repositories.UserRepository;
import in.springframework.blog.tutorials.utils.HTTPUtils;
import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.utils.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


/*@Component*/
public class AuthenticationParameterExtractionFilter implements Filter {
  public static final String TENANT_HEADER = "X-tenant";

  @Value("${tutorial.default.tenant.discriminator:default}")
  private String defaultDiscriminator;

  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    WebApplicationContext webApplicationContext
            = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    UserRepository userRepository = webApplicationContext.getBean(UserRepository.class);

    Optional<String> token = HTTPUtils.getOptionalParameterOrHeader(httpServletRequest,MyConstants.TOKEN_HEADER);
    Optional<String> username = HTTPUtils.getOptionalParameterOrHeader(httpServletRequest,MyConstants.USERNAME_HEADER);
    Optional<String> password = HTTPUtils.getOptionalParameterOrHeader(httpServletRequest,MyConstants.PASSWORD_HEADER);
    if (token.isPresent()) {
      RequestContext.incomingToken.set(token.get());
    }
    if (!username.isPresent()) {
      throw new AccessDeniedException(String.format("%s header is not present in the request", MyConstants.USERNAME_HEADER));
    }
    RequestContext.incomingUsername.set(username.get());
    if (password.isPresent()) {
      RequestContext.incomingPassword.set(password.get());
    }
    Optional<User> optionalUser
            = userRepository.findUserByTenantAndUsername(RequestContext.currentTenant.get(), username.get());
    if (optionalUser.isPresent()) {
      RequestContext.currentUser.set(optionalUser.get());
    }
    chain.doFilter(request, response);
  }
}
