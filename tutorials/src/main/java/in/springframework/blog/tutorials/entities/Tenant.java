package in.springframework.blog.tutorials.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "tenants",
        uniqueConstraints =
                {
                        @UniqueConstraint(name = "uq_discriminator", columnNames = {"discriminator"}),
                        @UniqueConstraint(name = "uq_name", columnNames = {"name"})
                })
public class Tenant extends AbstractBaseEntity{

  public Tenant() {

  }

  private String name;
  private String discriminator;
  private String description;
  @OneToMany(mappedBy = "tenant")
  private Set<User> users;
  private boolean defaultValue;
}
