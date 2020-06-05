package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.user.domain.Profile;
import in.springframework.blog.tutorials.user.domain.User;
import in.springframework.blog.tutorials.user.repository.ProfileRepository;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/profile")
public class ProfileEndpoint {

    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    @RequestMapping(value = "/{idOrUserNameOrEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Profile> getUser(@PathVariable("idOrUserNameOrEmail") String idOrUserNameOrEmail) {
        return profileService.retrieveProfile(idOrUserNameOrEmail);
    }
    @RequestMapping(value = "/{idOrUserNameOrEmail}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(MyConstants.ANNOTATION_ROLE_USER)
    public Optional<Profile> createProfile(@RequestHeader(value="username") String username, @RequestBody Profile profile) {
        return profileService.createProfile(username, profile);
    }
}
