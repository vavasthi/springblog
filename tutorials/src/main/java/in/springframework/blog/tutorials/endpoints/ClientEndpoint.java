package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.entities.TutorialClientDetails;
import in.springframework.blog.tutorials.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/oauth/client")
public class ClientEndpoint {

    @Autowired
    private ClientService clientService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN')")
    public Iterable<TutorialClientDetails> getClients() {
        return clientService.findAll();
    }
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN')")
    public Optional<TutorialClientDetails> getClient(@PathVariable("clientId") String clientId) {
        return clientService.findById(clientId);
    }
}
