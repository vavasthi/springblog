package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.repositories.TenantRepository;
import in.springframework.blog.tutorials.repositories.UserRepository;
import in.springframework.blog.tutorials.utils.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TenantRepository tenantRepository;

  public Iterable<User> findAll() {
    return userRepository.findAll();
  }
  public Optional<User> findByUsername(String username) {
    return userRepository.findUserByTenantAndUsername(RequestContext.currentTenant.get(), username);
  }
  public Optional<User> retrieveUser(String idOrUserNameOrEmail) {
    Optional<Tenant> optionalTenant = tenantRepository.findById(RequestContext.currentTenant.get().getId());
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
    optionalUser = userRepository.findUserByUsernameAndTenant(optionalTenant.get(),
            idOrUserNameOrEmail);
    return optionalUser;
  }
  public User save(User user) {
    return userRepository.save(user);
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
