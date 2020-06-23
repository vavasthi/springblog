package in.springframework.blog.tutorials.configs;

import in.springframework.blog.tutorials.filters.TenantHeaderFilter;
import in.springframework.blog.tutorials.pojos.Role;
import in.springframework.blog.tutorials.utils.MyConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TutorialWebServerSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/manage/**)");
    web.ignoring().antMatchers("/manage/info");
    web.ignoring().antMatchers("/webjars/**");
    web.ignoring().antMatchers("/error");
    web.ignoring().antMatchers("/swagger-ui.html");
    web.ignoring().antMatchers("/v2/api-docs/**");
    web.ignoring().antMatchers("/swagger-resources/**");
    web.ignoring().antMatchers("/oauth/client/**");
    web.ignoring().antMatchers("/*/registration/user");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().
            sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
            and().
            authorizeRequests().
            antMatchers(actuatorEndpoints()).hasAuthority(Role.ADMIN.name()).
            anyRequest().authenticated().
            and().
            anonymous().disable().
            exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());;
            http.addFilterBefore(new TenantHeaderFilter(), SecurityContextPersistenceFilter.class);
/*        http.addFilterAfter(new CurrentUserExtractionFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new TenantHeaderFilter(), CurrentUserExtractionFilter.class);*/

/*        http.addFilterBefore(new TenantHeaderFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new AuthenticationParameterExtractionFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new SufficientAuthenticationParametersPresentFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);*/
  }

  private String[] actuatorEndpoints() {
    return new String[]{
            MyConstants.AUTOCONFIG_ENDPOINT,
            MyConstants.BEANS_ENDPOINT,
            MyConstants.CONFIGPROPS_ENDPOINT,
            MyConstants.ENV_ENDPOINT,
            MyConstants.MAPPINGS_ENDPOINT,
            MyConstants.METRICS_ENDPOINT,
            MyConstants.SHUTDOWN_ENDPOINT
    };
  }

  @Bean
  public AuthenticationEntryPoint unauthorizedEntryPoint() {
    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
