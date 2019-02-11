package in.springframework.blog.tutorials;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "otherEntityManagerFactory",
        transactionManagerRef = "otherTransactionManager",
        basePackages = { "in.springframework.blog.tutorials.other.repository" }
)
public class OtherDatabaseConfig {

    @Value("${other.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${other.datasource.url}")
    private String url;
    @Value("${other.datasource.username}")
    private String username;
    @Value("${other.datasource.password}")
    private String password;
    @Value("${other.jpa.hibernate.ddl-auto}")
    private String ddlAuto;
    @Value("${other.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Bean(name = "otherDataSource")
    @ConfigurationProperties(prefix = "other.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).build();
    }

    @Bean(name = "otherEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    otherEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("otherDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.dialect", dialect);

        return builder
                .dataSource(dataSource)
                .packages("in.springframework.blog.tutorials.other.domain")
                .persistenceUnit("otherPu")
                .properties(properties)
                .build();
    }
    @Bean(name = "otherTransactionManager")
    public PlatformTransactionManager otherTransactionManager(
            @Qualifier("otherEntityManagerFactory") EntityManagerFactory
                    otherEntityManagerFactory
    ) {
        return new JpaTransactionManager(otherEntityManagerFactory);
    }}
