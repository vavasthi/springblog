package in.springframework.blog.tutorials.user.repository;

import in.springframework.blog.tutorials.user.domain.User;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u where u.email=:email")
    Optional<User> findUserByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u where u.email=:emailOrUsername or u.username = :emailOrUsername")
    Optional<User> findUserByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
    @Query("SELECT u FROM User u where u.username=:username")
    Optional<User> findUserByUsername(@Param("username") String username);
    @Query("SELECT u FROM User u where u.authToken=:authToken")
    Optional<User> findUserByAuthToken(@Param("authToken") String AuthToken);
}
