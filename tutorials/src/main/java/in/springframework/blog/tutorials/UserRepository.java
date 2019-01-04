package in.springframework.blog.tutorials;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u where u.email=:email")
    Optional<User> findUserByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u where u.email=:emailOrUsername or u.username = :emailOrUsername")
    Optional<User> findUserByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
    @Query("SELECT u FROM User u where u.username=:username")
    Optional<User> findUserByUsername(@Param("username") String username);
    @Query("SELECT u FROM User u where u.authToken=:authToken")
    Optional<User> findUserByAuthToken(@Param("authToken") String AuthToken);
}
