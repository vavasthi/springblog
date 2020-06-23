package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.services.ClientService;
import in.springframework.blog.tutorials.services.TenantService;
import in.springframework.blog.tutorials.utils.MyConstants;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
@Order(MyConstants.SEED_DATA_PRECEDENCE)
public class SeedDataInitializationFilter implements Filter {
  public static final String TENANT_HEADER = "X-tenant";
  private static final String defaultClient = "supersecretclient";
  private static final String defaultSecret = "supersecretclient123";

  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    WebApplicationContext webApplicationContext
            = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    TenantService tenantService = webApplicationContext.getBean(TenantService.class);
    ClientService clientService = webApplicationContext.getBean(ClientService.class);
    if (tenantService.count() == 0) {

      Tenant tenant = new Tenant();
      tenant.setDescription("Default tenant for the system");
      tenant.setDiscriminator("default");
      tenant.setName("Default Tenant");
      tenant.setDefaultValue(true);

    }
    chain.doFilter(request, response);
  }
}