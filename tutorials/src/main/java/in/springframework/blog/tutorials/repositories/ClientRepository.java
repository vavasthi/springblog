package in.springframework.blog.tutorials.repositories;

import in.springframework.blog.tutorials.entities.TutorialClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<TutorialClientDetails, String> {
}
