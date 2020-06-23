package in.springframework.blog.tutorials.configs;

import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EntityAuditConfig {
  @Autowired
  private UserService userService;

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAware<String>() {

      @Override
      public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
          return Optional.of(MyConstants.UNAUTHENTICATED_USER);
        }
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
      }
    };
  }
}
