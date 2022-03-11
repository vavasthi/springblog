package in.springframework.blog.tutorials.configs;

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

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class CacheMultitenantConfig {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Map<String, JedisConnectionFactory> jedisConnectionFactories;

    private  final String TENANT_PROPERTIES_RESOURCE = "classpath:tenants/*.properties";

    @Bean(name = "JedisConnectionFactories")
    private Map<String, JedisConnectionFactory> populateFactories() throws IOException {
        Map<String, JedisConnectionFactory> resolvedFactories = new HashMap<>();
        Resource[] resources = applicationContext.getResources(TENANT_PROPERTIES_RESOURCE);
        for (Resource resource : resources) {
            File propertyFile = resource.getFile();
            Properties tenantProperties = new Properties();
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(this.getClass().getClassLoader());

            try {
                tenantProperties.load(new FileInputStream(propertyFile));
                String tenantId = tenantProperties.getProperty("spring.datasource.name");

                String[] hosts = tenantProperties.getProperty("redis.nodes").split(",");
                String password = tenantProperties.getProperty("redis.password");
                int database = Integer.parseInt(tenantProperties.getProperty("redis.database"));
                int maxIdle = Integer.parseInt(tenantProperties.getProperty("redis.pool.maxIdle"));
                int minIdle = Integer.parseInt(tenantProperties.getProperty("redis.pool.minIdle"));
                int maxRedirects = Integer.parseInt(tenantProperties.getProperty("redis.pool.maxRedirects"));
                int maxTotal = Integer.parseInt(tenantProperties.getProperty("redis.pool.maxTotal"));
                int maxWaitMillis = Integer.parseInt(tenantProperties.getProperty("redis.pool.maxWaitMillis"));

                resolvedFactories.put(tenantId, jedisConnectionFactory(hosts, password, maxIdle, minIdle, maxWaitMillis, maxTotal, maxRedirects, database));
            } catch (IOException e) {
                // Ooops, tenant could not be loaded. This is bad.
                // Stop the application!
                e.printStackTrace();
                return null;
            }
        }
        return resolvedFactories;
    }

    private JedisConnectionFactory jedisConnectionFactory(String[] hosts,
                                                          String password,
                                                          int maxIdle,
                                                          int minIdle,
                                                          int maxWaitMillis,
                                                          int maxTotal,
                                                          int maxRedirects,
                                                          int database) {

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setMaxTotal(maxTotal);

        if (hosts.length == 1) {
            RedisStandaloneConfiguration standaloneConfiguration
                    = new RedisStandaloneConfiguration();
            standaloneConfiguration.setDatabase(database);
            String[] hostPort = hosts[0].split(":");
            if (hostPort.length > 1) {
                standaloneConfiguration.setPort(Integer.valueOf(hostPort[1]));
            }
            standaloneConfiguration.setHostName(hostPort[0]);
            if (password != null) {

                standaloneConfiguration.setPassword(RedisPassword.of(password));
            }

            JedisConnectionFactory factory = new JedisConnectionFactory(standaloneConfiguration);
            factory.afterPropertiesSet();
            return factory;
        }
        else {

            RedisClusterConfiguration configuration = new RedisClusterConfiguration(Arrays.asList(hosts));
            if (password != null || !password.isEmpty()) {
                configuration.setPassword(RedisPassword.of(password));
            }
            configuration.setMaxRedirects(maxRedirects);
            JedisConnectionFactory factory = new JedisConnectionFactory(configuration, poolConfig);
            factory.afterPropertiesSet();
            return factory;
        }
    }

    public RedisTemplate<KeyPrefixForCache, Object> redisTemplate() {
        RedisTemplate<KeyPrefixForCache, Object> redisTemplate = new RedisTemplate<KeyPrefixForCache, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactories.get(TutorialRequestContext.currentTenantDiscriminator.get()));
        redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
