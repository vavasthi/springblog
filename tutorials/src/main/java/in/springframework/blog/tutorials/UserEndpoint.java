package in.springframework.blog.tutorials;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LogManager.getLogger(UserEndpoint.class);

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostFilter("hasAuthority('ADMIN') or filterObject.authToken == authentication.name")
    public Iterable<User> getUsers() {
      logger.error(SecurityContextHolder.getContext().getAuthentication().getName());
      logger.error(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    @RequestMapping(value = "/{idOrUserNameOrEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail) {
        return retrieveUser(idOrUserNameOrEmail);
    }
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(MyConstants.ANNOTATION_ROLE_NEWUSER)
    public Optional<User> createUser(@RequestHeader(value="username") String username, @RequestBody User user) {
        user.setMask(Role.USER.ordinal());
        User storedUser = userRepository.save(user);
        storedUser.setPassword(null);
        return Optional.of(storedUser);
    }
    @RequestMapping(value = "/{idOrUserNameOrEmail}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> deleteUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail) {
        Optional<User> optionalUser = retrieveUser(idOrUserNameOrEmail);
        if (optionalUser.isPresent()) {

            userRepository.delete(optionalUser.get());
            return optionalUser;
        }
        throw new EntityNotFoundException(String.format("%s user not found.", idOrUserNameOrEmail));
    }
    @RequestMapping(value = "/{idOrUserNameOrEmail}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> patchUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail,
                                    @RequestBody User user) {
        Optional<User> optionalUser = retrieveUser(idOrUserNameOrEmail);
        if (optionalUser.isPresent()) {
            User storedUser = optionalUser.get();
            if (user.getEmail() != null) {
                storedUser.setEmail(user.getEmail());
            }
            if (user.getFullname() != null) {
                storedUser.setFullname(user.getFullname());
            }
            if (user.getPassword() != null) {
                storedUser.setPassword(user.getPassword());
            }
            return Optional.of(userRepository.save(storedUser));
        }
        throw new EntityNotFoundException(String.format(String.format("User with id %s is not found", idOrUserNameOrEmail)));
    }
    private Optional<User> retrieveUser(String idOrUserNameOrEmail) {
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
}
