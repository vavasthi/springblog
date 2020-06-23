package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.TutorialClientDetails;
import in.springframework.blog.tutorials.exceptions.EntityAlreadyExistsException;
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

  public Iterable<TutorialClientDetails> findAll() {
    return clientRepository.findAll();
  }

  public Optional<TutorialClientDetails> findById(String clientId) {
    return clientRepository.findById(clientId);
  }

  public Optional<TutorialClientDetails> create(TutorialClientDetails clientDetails) {

    Optional<TutorialClientDetails> optionalOauthClientDetails
            = clientRepository.findById(clientDetails.getClientId());
    if (optionalOauthClientDetails.isPresent()) {
      throw new EntityAlreadyExistsException(String.format("%s is not a valid client id.", clientDetails.getClientId()));
    }
    clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
    clientDetails = clientRepository.save(clientDetails);
    return Optional.of(clientDetails);
  }

  public Optional<TutorialClientDetails> update(String clientId, TutorialClientDetails clientDetails) {

    Optional<TutorialClientDetails> optionalOauthClientDetails
            = clientRepository.findById(clientId);
    if (!optionalOauthClientDetails.isPresent()) {
      throw new NotFoundException(String.format("%s is not a valid client id.", clientId));
    }
    TutorialClientDetails storedClientDetails = optionalOauthClientDetails.get();
    storedClientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
    return Optional.of(clientRepository.save(storedClientDetails));
  }

}
