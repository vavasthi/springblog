package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.user.domain.User;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public Iterable<User> findAll() {
    return userRepository.findAll();
  }
  public Optional<User> retrieveUser(String idOrUserNameOrEmail) {
    try {

      Long id = Long.parseLong(idOrUserNameOrEmail);
      Optional<User> optionalUser = userRepository.findById(id);
      if (optionalUser.isPresent()) {
        return optionalUser;
      }
    }
    catch(NumberFormatException e) {

    }
    Optional<User> optionalUser = userRepository.findUserByEmail(idOrUserNameOrEmail);
    if (optionalUser.isPresent()) {
      return optionalUser;
    }
    optionalUser = userRepository.findUserByUsername(idOrUserNameOrEmail);
    return optionalUser;
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public void delete(User user) {
    userRepository.delete(user);
  }
}
