package in.springframework.blog.tutorials.repositories;

import in.springframework.blog.tutorials.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    @Query("SELECT t FROM Tenant t where t.discriminator = :discriminator")
    Optional<Tenant> findTenantsByDiscriminator(@Param("discriminator") String discriminator);
}
