package in.springframework.blog.tutorials.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCreatedEvent {
  private long userId;
  private String username;
  private String fullname;
  private String status;
}
