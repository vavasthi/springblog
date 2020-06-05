package in.springframework.blog.tutorials.user.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBaseEntity {

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
}
