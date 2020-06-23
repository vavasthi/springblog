package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.exceptions.InsufficientAuthenticationParametersException;
import in.springframework.blog.tutorials.utils.TutorialRequestContext;

import javax.servlet.*;
import java.io.IOException;


/*@Component*/
public class SufficientAuthenticationParametersPresentFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {

    if (TutorialRequestContext.currentTenant.get() == null) {
      throw new InsufficientAuthenticationParametersException(String.format("Either password or token should be present"));
    }
    chain.doFilter(request, response);
  }
}