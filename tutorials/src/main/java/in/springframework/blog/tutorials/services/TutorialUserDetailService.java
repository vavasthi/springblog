package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.User;
import in.springframework.blog.tutorials.exceptions.NotFoundException;
import in.springframework.blog.tutorials.pojos.Role;
import in.springframework.blog.tutorials.pojos.TutorialGrantedAuthority;
import in.springframework.blog.tutorials.pojos.TutorialUserDetails;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class TutorialUserDetailService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws NotFoundException {
    log.error(String.format("%s looking for user.", username));
    Optional<User> user = userService.retrieveUser(username);
    if (user.isPresent()) {
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
    else {

      TutorialUserDetails userDetails = new TutorialUserDetails();
      userDetails.setUserName(username);
      userDetails.setPassword("NEWUSER");
      Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
      for (String authority : user.get().getGrantedAuthorities()) {
        authorities.add(new TutorialGrantedAuthority(Role.NEWUSER.name()));
      }
      userDetails.setGrantedAuthorities(authorities);
      return userDetails;
    }
  }
}
