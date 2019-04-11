package in.springframework.blog.tutorials.other.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "other")
public class Other {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String otherData;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }
}