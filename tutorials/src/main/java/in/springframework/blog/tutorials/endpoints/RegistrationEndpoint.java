package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.entities.TutorialClientDetails;
import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.mappers.UserPojoMapper;
import in.springframework.blog.tutorials.pojos.Role;
import in.springframework.blog.tutorials.pojos.UserPojo;
import in.springframework.blog.tutorials.services.ClientService;
import in.springframework.blog.tutorials.services.UserService;
import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.utils.TutorialRequestContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping(MyConstants.REGISTRATION_ENDPOINT)
public class RegistrationEndpoint {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPojoMapper userToPojoMapper;
    @Autowired
    private ClientService clientService;


    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<UserPojo> createUser(@PathParam(value="tenant") String tenant,
                                         @RequestBody UserPojo userPojo) {
        User user = userToPojoMapper.convert(userPojo);
        user.setMask(Role.USER.getMask());
        user.setTenant(TutorialRequestContext.currentTenant.get());
        user.setPassword(passwordEncoder.encode(userPojo.getPassword()));
        User storedUser = userService.save(user);
        storedUser.setPassword(null);
        return Optional.of(userToPojoMapper.convert(storedUser));
    }
    @RequestMapping(value = "/client", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN')")
    public Optional<TutorialClientDetails> getClient(@RequestBody TutorialClientDetails clientDetails) {
        return clientService.create(clientDetails);
    }
}
