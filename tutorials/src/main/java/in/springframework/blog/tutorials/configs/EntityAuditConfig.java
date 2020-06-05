package in.springframework.blog.tutorials.configs;

import in.springframework.blog.tutorials.RequestContext;
import in.springframework.blog.tutorials.user.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EntityAuditConfig {
  @Bean
  public AuditorAware<Long> auditorAware() {
    return new AuditorAware<Long>() {

      @Override
      public Optional<Long> getCurrentAuditor() {
          return Optional.of(RequestContext.currentUser.get().getId());
      }
    };
  }
}
