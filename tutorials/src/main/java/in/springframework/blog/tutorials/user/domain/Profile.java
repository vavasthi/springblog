package in.springframework.blog.tutorials.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(name = "profile")
@EntityListeners(AuditingEntityListener.class)
public class Profile extends AbstractBaseEntity{

  private Long userId;
  private String photo;
}
