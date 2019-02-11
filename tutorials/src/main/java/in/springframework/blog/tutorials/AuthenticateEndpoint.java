package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.user.domain.User;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateEndpoint {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestHeader(value="username") String username,
                               @RequestHeader(value="password") String password,
                               @RequestBody LoginRequest loginRequest) {
        User user = RequestContext.currentUser.get();
        return new LoginResponse(user.getAuthToken(), user.getExpiry());
    }
    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public LoginResponse logout(@RequestBody LoginRequest loginRequest) {
        User user = RequestContext.currentUser.get();
        user = userRepository.findById(user.getId()).get();
        user.setAuthToken(null);
        user.setExpiry(null);
        RequestContext.currentUser.remove();
        return new LoginResponse(user.getAuthToken(), user.getExpiry());
    }
}
