package in.springframework.blog.tutorials.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints =
          {
                  @UniqueConstraint(name = "uq_email", columnNames = {"email"}),
                  @UniqueConstraint(name = "uq_authToken", columnNames = {"authToken"}),
                  @UniqueConstraint(name = "uq_username", columnNames = {"username"})
          })
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @CreatedBy
    private Long createdBy;
    @LastModifiedBy
    private Long updatedBy;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name="tenantId", nullable = false)
    private Tenant tenant;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private long mask;
    private String authToken;
    private Date expiry;
    @ElementCollection
    private Set<String> grantedAuthorities;
}