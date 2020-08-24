package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.mappers.UserPojoMapper;
import in.springframework.blog.tutorials.pojos.Role;
import in.springframework.blog.tutorials.pojos.UserPojo;
import in.springframework.blog.tutorials.publishers.UserEventPublisher;
import in.springframework.blog.tutorials.repositories.TenantRepository;
import in.springframework.blog.tutorials.repositories.UserRepository;
import in.springframework.blog.tutorials.utils.TutorialRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TenantRepository tenantRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserPojoMapper userToPojoMapper;
  @Autowired
  private UserEventPublisher userEventPublisher;

  public Iterable<User> findAll() {
    return userRepository.findAll();
  }
  public Optional<User> findByUsername(String username) {
    return userRepository.findUserByTenantAndUsername(TutorialRequestContext.currentTenant.get(), username);
  }
  public Optional<User> retrieveUser(String idOrUserNameOrEmail) {
    Optional<Tenant> optionalTenant = tenantRepository.findById(TutorialRequestContext.currentTenant.get().getId());
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

  @Transactional
  public Optional<UserPojo> createUser(UserPojo userPojo) {
    User user = userToPojoMapper.convert(userPojo);
    user.setMask(Role.USER.getMask());
    user.setTenant(TutorialRequestContext.currentTenant.get());
    user.setPassword(passwordEncoder.encode(userPojo.getPassword()));
    User storedUser = save(user);
    storedUser.setPassword(null);
    userEventPublisher.publishUserCreatedEvent(storedUser);
    return Optional.of(userToPojoMapper.convert(storedUser));
  }
}
