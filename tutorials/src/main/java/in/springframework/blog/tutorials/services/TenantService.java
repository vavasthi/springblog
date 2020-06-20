package in.springframework.blog.tutorials.services;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantService {
  @Autowired
  private TenantRepository tenantRepository;

  public Iterable<Tenant> findAll() {
    return tenantRepository.findAll();
  }
  public Optional<Tenant> findTenantsByDiscriminator(String discriminator) {
    return tenantRepository.findTenantsByDiscriminator(discriminator);
  }

  public Tenant save(Tenant tenant) {
    return tenantRepository.save(tenant);
  }

  public void delete(Tenant tenant) {
    tenantRepository.delete(tenant);
  }

}
