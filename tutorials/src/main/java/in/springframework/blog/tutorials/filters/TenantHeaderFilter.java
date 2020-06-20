package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.services.TenantService;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.utils.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


@Component
public class TenantHeaderFilter implements Filter {
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
    TenantService tenantService = webApplicationContext.getBean(TenantService.class);
    String tenantDiscriminator = httpServletRequest.getHeader(TENANT_HEADER);
    if (tenantDiscriminator == null || tenantDiscriminator.isEmpty()) {
      tenantDiscriminator = webApplicationContext.getEnvironment().getProperty(MyConstants.DEFAULT_DISCRIMINATOR);
    }
    Optional<Tenant> optionalTenant = tenantService.findTenantsByDiscriminator(tenantDiscriminator);
    if (!optionalTenant.isPresent()) {
      Tenant tenant = new Tenant();
      tenant.setDescription("Default tenant for the system");
      tenant.setDiscriminator("default");
      tenant.setName("Default Tenant");
      tenant.setDefaultValue(true);

      tenant = tenantService.save(tenant);
      optionalTenant = Optional.of(tenant);
      User user = new User();
      user.setEmail(MyConstants.DEFAULT_USER_EMAIL);
      user.setFullname(MyConstants.DEFAULT_USER_FULLNAME);
      user.setPassword(MyConstants.DEFAULT_USER_PASSWORD);
      user.setUsername(MyConstants.DEFAULT_USERNAME);
      user.setTenant(tenant);

      UserService userService = webApplicationContext.getBean(UserService.class);
      user = userService.save(user);
      RequestContext.currentUser.set(user);
    }
    RequestContext.currentTenant.set(optionalTenant.get());
    chain.doFilter(request, response);
  }
}