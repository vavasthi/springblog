package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.OauthClientDetails;
import in.springframework.blog.tutorials.exceptions.NotFoundException;
import in.springframework.blog.tutorials.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Iterable<OauthClientDetails> findAll() {
    return clientRepository.findAll();
  }

  public Optional<OauthClientDetails> findById(String clientId) {
    return clientRepository.findById(clientId);
  }

  public Optional<OauthClientDetails> update(String clientId, OauthClientDetails clientDetails) {

    Optional<OauthClientDetails> optionalOauthClientDetails
            = clientRepository.findById(clientId);
    if (!optionalOauthClientDetails.isPresent()) {
      throw new NotFoundException(String.format("%s is not a valid client id.", clientId));
    }
    OauthClientDetails storedClientDetails = optionalOauthClientDetails.get();
    storedClientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
    return Optional.of(clientRepository.save(storedClientDetails));
  }
}
