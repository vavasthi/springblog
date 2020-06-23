package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class TenantService {
  @Autowired
  private TenantRepository tenantRepository;
  @PersistenceContext
  private EntityManager entityManager;

  public Iterable<Tenant> findAll() {
    return tenantRepository.findAll();
  }
  public Optional<Tenant> findTenantsByDiscriminator(String discriminator) {
    return tenantRepository.findTenantsByDiscriminator(discriminator);
  }
  public Optional<Tenant> retrieveTenantsByDiscriminator(String discriminator) {
    Optional<Tenant> optionalTenant = tenantRepository.findTenantsByDiscriminator(discriminator);
    return optionalTenant;
  }

  public Tenant save(Tenant tenant) {
    return tenantRepository.save(tenant);
  }

  public void delete(Tenant tenant) {
    tenantRepository.delete(tenant);
  }

}
