package in.springframework.blog.tutorials.configs;

import in.springframework.blog.tutorials.utils.TutorialRequestContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TutorialMultitenantDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {

    if (TutorialRequestContext.currentTenantDiscriminator.get() != null) {
      return TutorialRequestContext.currentTenantDiscriminator.get();
    }
    else {
      return "unknown";
    }
  }
}
