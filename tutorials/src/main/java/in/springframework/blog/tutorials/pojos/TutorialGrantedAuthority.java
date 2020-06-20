package in.springframework.blog.tutorials.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TutorialGrantedAuthority implements GrantedAuthority, Serializable {

  private String authority;
}
