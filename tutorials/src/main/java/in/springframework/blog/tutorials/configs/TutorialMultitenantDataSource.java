package in.springframework.blog.tutorials.configs;

import in.springframework.blog.tutorials.utils.RequestContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TutorialMultitenantDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {

    if (RequestContext.currentTenantDiscriminator.get() != null) {
      return RequestContext.currentTenantDiscriminator.get();
    }
    else {
      return "unknown";
    }
  }
}
