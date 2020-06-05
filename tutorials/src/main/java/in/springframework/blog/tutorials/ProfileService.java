package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.user.domain.Profile;
import in.springframework.blog.tutorials.user.domain.User;
import in.springframework.blog.tutorials.user.repository.ProfileRepository;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private ProfileRepository profileRepository;

  public Iterable<Profile> findAll() {
    return profileRepository.findAll();
  }
  public Optional<Profile> retrieveProfile(String idOrUserNameOrEmail) {

    Optional<User> optionalUser = userService.retrieveUser(idOrUserNameOrEmail);
    if (optionalUser.isPresent()) {

      Optional<Profile> optionalProfile = profileRepository.findProfileByUserId(optionalUser.get().getId());
      if (optionalProfile.isPresent()) {
        return optionalProfile;
      }
    }
    throw new NotFoundException(String.format("User %s doesn't have a valid profile present.", idOrUserNameOrEmail));
  }

  public Profile save(Profile profile) {
    return profileRepository.save(profile);
  }

  public void delete(Profile profile) {
    profileRepository.delete(profile);
  }

  public Optional<Profile> createProfile(String username, Profile profile) {

    Optional<User> optionalUser = userService.retrieveUser(username);
    if (optionalUser.isPresent()) {

      Optional<Profile> optionalProfile = profileRepository.findProfileByUserId(optionalUser.get().getId());
      if (optionalProfile.isPresent()) {
        throw new EntityAlreadyExistsException(String.format("Profile already exists for user %s", username));
      }
      profile.setUserId(optionalUser.get().getId());
      return Optional.of(profileRepository.save(profile));
    }
    throw new NotFoundException(String.format("User %s doesn't exist", username));
  }
}
