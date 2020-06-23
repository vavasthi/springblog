package in.springframework.blog.tutorials.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints =
          {
                  @UniqueConstraint(name = "uq_email", columnNames = {"email"}),
                  @UniqueConstraint(name = "uq_authToken", columnNames = {"authToken"}),
                  @UniqueConstraint(name = "uq_username", columnNames = {"username"})
          })
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    public User() {

    }
    public User(Tenant tenant, String fullname, String username, String email, long mask, String authToken, Date expiry) {
        this.tenant = tenant;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.mask = mask;
        this.authToken = authToken;
        this.expiry = expiry;

    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="tenantId", nullable = false)
    private Tenant tenant;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private long mask;
    private String authToken;
    private Date expiry;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> grantedAuthorities;
}