package in.springframework.blog.tutorials.pojos;

import lombok.Getter;

@Getter
public class UserChangePassword {

  private Long id;
  private String username;
  private String email;
  private String oldPassword;
  private String newPassword;
}
