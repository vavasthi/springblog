package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.services.ProfileService;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.entities.Profile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
