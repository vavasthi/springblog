package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.utils.RequestContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

@Component
@Order(MyConstants.USER_PRECEDENCE)
public class CurrentUserExtractionFilter implements Filter {
  public static final String TENANT_HEADER = "X-tenant";

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    WebApplicationContext webApplicationContext
            = WebApplicationContextUtils.getWebApplicationContext(servletRequest.getServletContext());
    UserService userService = webApplicationContext.getBean(UserService.class);
    if (SecurityContextHolder.getContext().getAuthentication() != null) {

      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String username = null;
      if (principal instanceof org.springframework.security.core.userdetails.User) {
        username = ((org.springframework.security.core.userdetails.User)principal).getUsername();
      }
      else if (principal instanceof String) {
        username = (String)principal;
      }
      Optional<User> optionalUser = userService.findByUsername(username);
      if (optionalUser.isPresent()) {

        RequestContext.currentUser.set(optionalUser.get());
      }
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}