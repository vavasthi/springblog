package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.annotations.AdminTenantAdminAllOrCurrentUserListPermission;
import in.springframework.blog.tutorials.annotations.AdminTenantAdminOrCurrentUserBodyPermission;
import in.springframework.blog.tutorials.annotations.AdminTenantAdminOrCurrentUserPermission;
import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.mappers.UserPojoMapper;
import in.springframework.blog.tutorials.pojos.UserChangePassword;
import in.springframework.blog.tutorials.pojos.UserPojo;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.MyConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Log4j2
@RestController
@RequestMapping(MyConstants.USER_ENDPOINT)
public class UserEndpoint {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPojoMapper userToPojoMapper;


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @AdminTenantAdminAllOrCurrentUserListPermission
    public Iterable<UserPojo> getUsers() {
        Iterable<User> users = userService.findAll();
        final Set<UserPojo> pojoSet = new HashSet<>();
        users.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                pojoSet.add(userToPojoMapper.convert(user));
            }
        });
        return pojoSet;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @AdminTenantAdminOrCurrentUserPermission
    public Optional<UserPojo> getUser(@PathVariable("username") String username) {
        return userToPojoMapper.convert(userService.retrieveUser(username));
    }
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @AdminTenantAdminOrCurrentUserPermission
    public Optional<UserPojo> deleteUser(@PathVariable("username") String username) {
        Optional<User> optionalUser = userService.retrieveUser(username);
        if (optionalUser.isPresent()) {

            userService.delete(optionalUser.get().getId());
            return Optional.of(userToPojoMapper.convert(optionalUser.get()));
        }
        throw new EntityNotFoundException(String.format("%s user not found.", username));
    }
    @RequestMapping(value = "/changePassword/{username}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @AdminTenantAdminOrCurrentUserPermission
    public Optional<UserPojo> changePassword(@PathVariable("username") String username,
                                             @RequestBody UserChangePassword user) {
        Optional<User> optionalUser = userService.retrieveUser(username);
        if (optionalUser.isPresent()) {
            User storedUser = optionalUser.get();
            if (passwordEncoder.matches(user.getOldPassword(), storedUser.getPassword())) {
                storedUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
            }
            storedUser = userService.save(storedUser);
            storedUser.setPassword(null);
            return Optional.of(userToPojoMapper.convert(storedUser));
        }
        throw new EntityNotFoundException(String.format(String.format("User with id %s is not found", username)));
    }

    @RequestMapping(value = "/{idOrUserNameOrEmail}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @AdminTenantAdminOrCurrentUserBodyPermission
    public Optional<UserPojo> patchUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail,
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
            return Optional.of(userToPojoMapper.convert(userService.save(storedUser)));
        }
        throw new EntityNotFoundException(String.format(String.format("User with id %s is not found", idOrUserNameOrEmail)));
    }
}
