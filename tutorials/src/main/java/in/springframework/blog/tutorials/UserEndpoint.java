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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id);
    }
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@RequestBody User user) {
        return Optional.of(userRepository.save(user));
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> deleteUser(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return optionalUser;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> patchUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);
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
        throw new EntityNotFoundException(String.format(String.format("User with id %d is not found", id)));
    }
}
