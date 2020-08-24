package in.springframework.blog.tutorials.publishers;

import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.events.UserCreatedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserEventPublisher {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  public void publishUserCreatedEvent(final User user) {
    log.info(String.format("Publishing event for user %s with id %d", user.getUsername(), user.getId()));
    applicationEventPublisher.publishEvent(UserCreatedEvent.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .fullname(user.getFullname())
            .build()
    );
  }
}
