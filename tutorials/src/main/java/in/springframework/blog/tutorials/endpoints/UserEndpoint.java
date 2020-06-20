package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.pojos.Role;
import in.springframework.blog.tutorials.pojos.UserChangePassword;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.RequestContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserEndpoint {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostFilter("hasAuthority('ADMIN') or filterObject.authToken == authentication.name")
    public Iterable<User> getUsers() {
      log.error(SecurityContextHolder.getContext().getAuthentication().getName());
      log.error(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Iterable<User> users = userService.findAll();
        return users;
    }

    @RequestMapping(value = "/{idOrUserNameOrEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail) {
        return userService.retrieveUser(idOrUserNameOrEmail);
    }
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize(MyConstants.ANNOTATION_ROLE_NEWUSER)
    public Optional<User> createUser(@RequestHeader(value="username") String username, @RequestBody User user) {
        user.setMask(Role.USER.ordinal());
        user.setUsername(username);
        user.setTenant(RequestContext.currentTenant.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User storedUser = userService.save(user);
        storedUser.setPassword(null);
        return Optional.of(storedUser);
    }
    @RequestMapping(value = "/{idOrUserNameOrEmail}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> deleteUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail) {
        Optional<User> optionalUser = userService.retrieveUser(idOrUserNameOrEmail);
        if (optionalUser.isPresent()) {

            userService.delete(optionalUser.get());
            return optionalUser;
        }
        throw new EntityNotFoundException(String.format("%s user not found.", idOrUserNameOrEmail));
    }
    @RequestMapping(value = "/changePassword/{idOrUserNameOrEmail}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> changePassword(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail,
                                         @RequestBody UserChangePassword ucp) {
        Optional<User> optionalUser = userService.retrieveUser(idOrUserNameOrEmail);
        if (optionalUser.isPresent()) {
            User storedUser = optionalUser.get();
//            if (passwordEncoder.matches(ucp.getOldPassword(), storedUser.getPassword())) {
                storedUser.setPassword(passwordEncoder.encode(ucp.getNewPassword()));
//            }
            storedUser = userService.save(storedUser);
            storedUser.setPassword(null);
            return Optional.of(storedUser);
        }
        throw new EntityNotFoundException(String.format(String.format("User with id %s is not found", idOrUserNameOrEmail)));
    }

    @RequestMapping(value = "/{idOrUserNameOrEmail}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> patchUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail,
                                    @RequestBody User user) {
        Optional<User> optionalUser = userService.retrieveUser(idOrUserNameOrEmail);
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
            return Optional.of(userService.save(storedUser));
        }
        throw new EntityNotFoundException(String.format(String.format("User with id %s is not found", idOrUserNameOrEmail)));
    }
}