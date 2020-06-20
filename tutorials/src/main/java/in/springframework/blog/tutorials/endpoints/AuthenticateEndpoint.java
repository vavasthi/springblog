package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.pojos.LoginRequest;
import in.springframework.blog.tutorials.pojos.LoginResponse;
import in.springframework.blog.tutorials.utils.RequestContext;
import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.repositories.UserRepository;
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
