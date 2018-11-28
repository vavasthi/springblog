package in.springframework.blog.tutorials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{idOrUserNameOrEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail) {
        return retrieveUser(idOrUserNameOrEmail);
    }
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@RequestBody User user) {
        return Optional.of(userRepository.save(user));
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
