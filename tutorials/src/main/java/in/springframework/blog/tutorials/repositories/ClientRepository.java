package in.springframework.blog.tutorials.repositories;

import in.springframework.blog.tutorials.entities.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<OauthClientDetails, String> {
}
