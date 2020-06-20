package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.entities.OauthClientDetails;
import in.springframework.blog.tutorials.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/oauth/client")
public class ClientEndpoint {

    @Autowired
    private ClientService clientService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<OauthClientDetails> getClients() {
        return clientService.findAll();
    }
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<OauthClientDetails> getClient(@PathVariable("clientId") String clientId) {
        return clientService.findById(clientId);
    }
    @RequestMapping(value = "/{clientId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<OauthClientDetails> getClient(@PathVariable("clientId") String clientId,
                                                  @RequestBody OauthClientDetails clientDetails) {
        return clientService.update(clientId, clientDetails);
    }

}
