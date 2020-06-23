package in.springframework.blog.tutorials.filters;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.entities.TutorialClientDetails;
import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.pojos.Role;
import in.springframework.blog.tutorials.pojos.TutorialGrantedAuthority;
import in.springframework.blog.tutorials.services.ClientService;
import in.springframework.blog.tutorials.services.TenantService;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.utils.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
    String[] dirs = httpServletRequest.getRequestURI().split(File.separator);
    String tenantDiscriminatorInPath = dirs[1];
    WebApplicationContext webApplicationContext
            = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    TenantService tenantService = webApplicationContext.getBean(TenantService.class);
    ClientService clientService = webApplicationContext.getBean(ClientService.class);
    String tenantDiscriminator = httpServletRequest.getHeader(TENANT_HEADER);
    if (tenantDiscriminator == null || tenantDiscriminator.isEmpty() || !tenantDiscriminatorInPath.equals(tenantDiscriminator)) {
      tenantDiscriminator = webApplicationContext.getEnvironment().getProperty(MyConstants.DEFAULT_DISCRIMINATOR);
    }
    Optional<Tenant> optionalTenant = tenantService.retrieveTenantsByDiscriminator(tenantDiscriminator);
    if (!optionalTenant.isPresent()) {
      Tenant tenant = new Tenant();
      tenant.setDescription("Default tenant for the system");
      tenant.setDiscriminator("default");
      tenant.setName("Default Tenant");
      tenant.setDefaultValue(true);
      tenantDiscriminator = "default";

      Optional<TutorialClientDetails> optionalClientDetails = clientService.findById(defaultClient);
      if (!optionalClientDetails.isPresent()) {
        TutorialClientDetails clientDetails = new TutorialClientDetails();
        clientDetails.setClientId(defaultClient);
        clientDetails.setClientSecret(defaultSecret);
        clientDetails.setAccessTokenValidity(500);
        clientDetails.setAdditionalInformation(new HashMap<>());
        clientDetails.getAdditionalInformation().put("Info1", "Supresecret client Additional information");
        clientDetails.setAuthorities(new HashSet<GrantedAuthority>(Arrays.asList(new TutorialGrantedAuthority(Role.ADMIN.name()),
                new TutorialGrantedAuthority(Role.TENANT_ADMIN.name()),
                new TutorialGrantedAuthority(Role.USER.name()))));
        clientDetails.setAuthorizedGrantTypes(new HashSet<>(Arrays.asList(MyConstants.GRANT_TYPES.password.name(),
                MyConstants.GRANT_TYPES.authorization_code.name(),
                MyConstants.GRANT_TYPES.refresh_token.name(),
                MyConstants.GRANT_TYPES.client_credentials.name())));
        clientDetails.setRefreshTokenValidity(1000);
        clientDetails.setAutoApprove(true);
        clientService.create(clientDetails);
        optionalClientDetails = Optional.of(clientDetails);
      }

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
    }
    RequestContext.currentTenant.set(tenantService.retrieveTenantsByDiscriminator(tenantDiscriminator).get());
    chain.doFilter(request, response);
  }
}