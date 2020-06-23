package in.springframework.blog.tutorials.mappers;

import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.pojos.UserPojo;
import in.springframework.blog.tutorials.utils.TutorialRequestContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPojoMapper {

  public Optional<UserPojo> convert(Optional<User> optionalUser) {

    if (optionalUser.isPresent()) {
      User u = optionalUser.get();
      return Optional.of(convert(u));
    }
    return Optional.empty();
  }
  public UserPojo convert(User u) {

    return new UserPojo(u.getId(),
            u.getCreatedBy(),
            u.getUpdatedBy(),
            u.getCreatedAt(),
            u.getUpdatedAt(),
            u.getTenant().getId(),
            u.getFullname(),
            u.getUsername(),
            null,
            u.getEmail(),
            u.getMask(),
            u.getAuthToken(),
            u.getExpiry(),
            u.getGrantedAuthorities());
  }
  public User convert(UserPojo userPojo) {

    return new User(TutorialRequestContext.currentTenant.get(),
            userPojo.getFullname(),
            userPojo.getUsername(),
            userPojo.getEmail(),
            userPojo.getMask(),
            userPojo.getAuthToken(),
            userPojo.getExpiry());
  }
}
