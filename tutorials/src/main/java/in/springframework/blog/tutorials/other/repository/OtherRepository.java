package in.springframework.blog.tutorials.other.repository;

import in.springframework.blog.tutorials.other.domain.Other;
import in.springframework.blog.tutorials.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OtherRepository extends JpaRepository<Other, Long> {
}
