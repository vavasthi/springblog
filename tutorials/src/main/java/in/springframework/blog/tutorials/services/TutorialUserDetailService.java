package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.exceptions.NotFoundException;
import in.springframework.blog.tutorials.pojos.TutorialGrantedAuthority;
import in.springframework.blog.tutorials.pojos.TutorialUserDetails;
import in.springframework.blog.tutorials.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TutorialUserDetailService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws NotFoundException {
    Optional<User> user = userRepository.findUserByUsername(username);
    if (user != null) {
      TutorialUserDetails userDetails = new TutorialUserDetails();
      userDetails.setUserName(user.get().getUsername());
      userDetails.setPassword(user.get().getPassword());
      Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
      for (String authority : user.get().getGrantedAuthorities()) {
        authorities.add(new TutorialGrantedAuthority(authority));
      }
      userDetails.setGrantedAuthorities(authorities);
      return userDetails;
    }
    throw new NotFoundException(username);
  }
}
