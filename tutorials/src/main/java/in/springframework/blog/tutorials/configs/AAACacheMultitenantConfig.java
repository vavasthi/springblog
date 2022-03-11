package in.springframework.blog.tutorials.configs;

import in.springframework.blog.tutorials.aaa.AAAInterface;
import in.springframework.blog.tutorials.pojos.KeyPrefixForCache;
import in.springframework.blog.tutorials.utils.TutorialRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class AAACacheMultitenantConfig {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Map<String, AAAInterface> aaaInterfaces;

    private  final String TENANT_PROPERTIES_RESOURCE = "classpath:tenants/*.properties";

    @Bean
    private Map<String, AAAInterface> populateFactories() throws IOException {
        Map<String, AAAInterface> aaaInterfaces = new HashMap<>();
        Resource[] resources = applicationContext.getResources(TENANT_PROPERTIES_RESOURCE);
        for (Resource resource : resources) {
            File propertyFile = resource.getFile();
            Properties tenantProperties = new Properties();

            try {
                tenantProperties.load(new FileInputStream(propertyFile));
                String tenantId = tenantProperties.getProperty("spring.datasource.name");
                String aaaBeanClassName = tenantProperties.getProperty("springblog.aaainterface");
                AAAInterface aaaInterface
                        = AAAInterface.class.cast(applicationContext.getBean(aaaBeanClassName));

                aaaInterfaces.put(tenantId, aaaInterface);
            } catch (IOException e) {
                // Ooops, tenant could not be loaded. This is bad.
                // Stop the application!
                e.printStackTrace();
                return null;
            }
        }
        return aaaInterfaces;
    }


    public AAAInterface aaaInterface() {
        return aaaInterfaces.get(aaaInterfaces
                .get(TutorialRequestContext
                        .currentTenantDiscriminator.get()));
    }

}
