package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.exceptions.InvalidTenantDiscriminatorException;
import in.springframework.blog.tutorials.services.TenantService;
import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.utils.TutorialRequestContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
@Order(MyConstants.TENANT_PRECEDENCE)
public class TenantFilter implements Filter {
  public static final String TENANT_HEADER = "X-tenant";
  private static final String defaultClient = "supersecretclient";
  private static final String defaultSecret = "supersecretclient123";

  @Value("${tutorial.default.tenant.discriminator:default}")
  private String defaultDiscriminator;

  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    HttpServletResponse httpServletResponse = (HttpServletResponse)response;
    WebApplicationContext webApplicationContext
            = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    TenantService tenantService = webApplicationContext.getBean(TenantService.class);
    String tenantDiscriminator = TutorialRequestContext.currentTenantDiscriminator.get();
    Optional<Tenant> optionalTenant = tenantService.retrieveTenantsByDiscriminator(tenantDiscriminator);
    if (!optionalTenant.isPresent()) {
      httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
      httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      Map<String, Object> errorDetail = new HashMap<>();
      errorDetail.put("message", String.format("Tenant %s doesn't exist", tenantDiscriminator));
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(httpServletResponse.getWriter(), errorDetail);
      throw new InvalidTenantDiscriminatorException(String.format("Tenant %s doesn't exist", tenantDiscriminator));
    }
    else {

      TutorialRequestContext.currentTenant.set(optionalTenant.get());
    }
    chain.doFilter(request, response);
  }
}