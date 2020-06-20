package in.springframework.blog.tutorials.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "profile")
@EntityListeners(AuditingEntityListener.class)
public class Profile extends AbstractBaseEntity{

  private Long userId;
  private String photo;
}
