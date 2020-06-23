package in.springframework.blog.tutorials.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef =  "multiTransactionManager",
        basePackages = {"in.springframework.blog.tutorials.repositories"})
@EntityScan(basePackages = {"in.springframework.blog.tutorials.entities"})
public class TutorialMultitenantConfig {

  @Autowired
  private DataSourceProperties dataSourceProperties;
  @Autowired
  private ApplicationContext applicationContext;
  private  final String TENANT_PROPERTIES_RESOURCE = "classpath:tenants/*.properties";

  @Primary
  @Bean(name = "dataSource")
  @ConfigurationProperties(
          prefix = "spring.datasource"
  )
  public DataSource dataSource() {
    Map<Object, Object> resolvedDataSources = new HashMap<>();
    try {
      Resource[] resources = applicationContext.getResources(TENANT_PROPERTIES_RESOURCE);
      for (Resource resource : resources) {
        File propertyFile = resource.getFile();
        Properties tenantProperties = new Properties();
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(this.getClass().getClassLoader());

        try {
          tenantProperties.load(new FileInputStream(propertyFile));

          String tenantId = tenantProperties.getProperty("spring.datasource.name");

          // Assumption: The tenant database uses the same driver class
          // as the default database that you configure.
          dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName())
                  .url(tenantProperties.getProperty("spring.datasource.url"))
                  .username(tenantProperties.getProperty("spring.datasource.username"))
                  .password(tenantProperties.getProperty("spring.datasource.password"));

          if (dataSourceProperties.getType() != null) {
            dataSourceBuilder.type(dataSourceProperties.getType());
          }

          resolvedDataSources.put(tenantId, dataSourceBuilder.build());
        } catch (IOException e) {
          // Ooops, tenant could not be loaded. This is bad.
          // Stop the application!
          e.printStackTrace();
          return null;
        }
      }

      // Create the final multi-tenant source.
      // It needs a default database to connect to.
      // Make sure that the default database is actually an empty tenant database.
      // Don't use that for a regular tenant if you want things to be safe!
      TutorialMultitenantDataSource dataSource = new TutorialMultitenantDataSource();
      dataSource.setDefaultTargetDataSource(defaultDataSource());
      dataSource.setTargetDataSources(resolvedDataSources);

      // Call this to finalize the initialization of the data source.
      dataSource.afterPropertiesSet();

      return dataSource;
    }
    catch (IOException e) {

    }
    return defaultDataSource();
  }

  /**
   * Creates the default data source for the application
   * @return
   */
  private DataSource defaultDataSource() {
    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(this.getClass().getClassLoader())
            .driverClassName(dataSourceProperties.getDriverClassName())
            .url(dataSourceProperties.getUrl())
            .username(dataSourceProperties.getUsername())
            .password(dataSourceProperties.getPassword());

    if(dataSourceProperties.getType() != null) {
      dataSourceBuilder.type(dataSourceProperties.getType());
    }

    return dataSourceBuilder.build();
  }

  @Bean(name = "multiEntityManager")
  public LocalContainerEntityManagerFactoryBean multiEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("in.springframework.blog.tutorials.entities");
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(hibernateProperties());
    return em;
  }
  @Bean(name = "multiTransactionManager")
  public PlatformTransactionManager multiTransactionManager() {
    JpaTransactionManager transactionManager
            = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
            multiEntityManager().getObject());
    return transactionManager;
  }

  @Primary
  @Bean(name = "dbSessionFactory")
  public LocalSessionFactoryBean dbSessionFactory() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource());
    sessionFactoryBean.setPackagesToScan("in.springframework.blog.tutorials.entities");
    sessionFactoryBean.setHibernateProperties(hibernateProperties());
    return sessionFactoryBean;
  }
  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.show_sql", true);
    properties.put("hibernate.format_sql", true);
    return properties;
  }
}
