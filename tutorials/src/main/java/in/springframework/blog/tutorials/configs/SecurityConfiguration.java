package in.springframework.blog.tutorials.configs;

import in.springframework.blog.tutorials.pojos.Role;
import in.springframework.blog.tutorials.providers.TokenAuthenticationProvider;
import in.springframework.blog.tutorials.providers.UsernamePasswordAuthenticationProvider;
import in.springframework.blog.tutorials.utils.MyConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by vinay on 1/27/16.
 */

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(99)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider());
        auth.authenticationProvider(usernamePasswordAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/manage/health");
        web.ignoring().antMatchers("/manage/info");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/error");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/oauth/client/**");
        web.ignoring().antMatchers("/user/**");
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

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }
    @Bean
    public AuthenticationProvider usernamePasswordAuthenticationProvider() {
        return new UsernamePasswordAuthenticationProvider();
    }

}