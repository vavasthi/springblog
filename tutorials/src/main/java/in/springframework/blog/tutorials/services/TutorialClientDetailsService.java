package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.TutorialClientDetails;
import in.springframework.blog.tutorials.repositories.ClientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class TutorialClientDetailsService implements ClientDetailsService {
  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

    Optional<TutorialClientDetails> optionalClientDetails = clientRepository.findById(clientId);
    if (optionalClientDetails.isPresent()) {
      return optionalClientDetails.get();
    }
    throw new ClientRegistrationException(String.format("%s client doesn't exist!", clientId));
  }
}
