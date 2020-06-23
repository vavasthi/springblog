package in.springframework.blog.tutorials.repositories;

import in.springframework.blog.tutorials.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("SELECT p FROM Profile p where p.userId = :userId")
    Optional<Profile> findProfileByUserId(@Param("userId") Long userId);
}
