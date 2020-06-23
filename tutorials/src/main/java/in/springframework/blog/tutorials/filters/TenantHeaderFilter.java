package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.exceptions.InvalidTenantDiscriminatorException;
import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.utils.RequestContext;
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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Order(MyConstants.TENANT_HEADER_PRECEDENCE)
public class TenantHeaderFilter implements Filter {
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
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    String[] dirs = httpServletRequest.getRequestURI().split(File.separator);
    String tenantDiscriminatorInPath = dirs[1];
    WebApplicationContext webApplicationContext
            = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    String tenantDiscriminator = httpServletRequest.getHeader(TENANT_HEADER);
    if (tenantDiscriminatorInPath.equals(tenantDiscriminator)) {
      RequestContext.currentTenantDiscriminator.set(tenantDiscriminator);
      chain.doFilter(request, response);
    }
    else {
      httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
      httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      Map<String, Object> errorDetail = new HashMap<>();
      errorDetail.put("message", String.format("The header X-tenant should be same as the tenant discriminator in URL. Currently are %s and %s ", tenantDiscriminator, tenantDiscriminatorInPath));
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(httpServletResponse.getWriter(), errorDetail);
      throw new InvalidTenantDiscriminatorException(String.format("The header X-tenant should be same as the tenant discriminator in URL. Currently are %s and %s ", tenantDiscriminator, tenantDiscriminatorInPath));
    }
  }
}