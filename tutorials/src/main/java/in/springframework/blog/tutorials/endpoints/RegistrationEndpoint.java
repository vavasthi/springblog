package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.entities.TutorialClientDetails;
import in.springframework.blog.tutorials.exceptions.EntityAlreadyExistsException;
import in.springframework.blog.tutorials.pojos.UserPojo;
import in.springframework.blog.tutorials.services.ClientService;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.MyConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping(MyConstants.REGISTRATION_ENDPOINT)
public class RegistrationEndpoint {

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;


    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<UserPojo> createUser(@PathParam(value="tenant") String tenant,
                                         @RequestBody UserPojo userPojo) {
        try {
            return userService.createUser(userPojo);

        }
        catch(DataIntegrityViolationException divx) {
            throw new EntityAlreadyExistsException(String.format("User %s or email %s already exists", userPojo.getUsername(), userPojo.getEmail()));
        }
    }
    @RequestMapping(value = "/client", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN')")
    public Optional<TutorialClientDetails> getClient(@RequestBody TutorialClientDetails clientDetails) {
        return clientService.create(clientDetails);
    }
}
