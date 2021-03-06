package in.springframework.blog.tutorials.utils;

import org.springframework.core.Ordered;

public class MyConstants {


    /**
     * The constant EXCEPTION_URL.
     */
    public static final String EXCEPTION_URL = "http://springframework.in/errors/%s";
   // Spring Boot Actuator services
    public static final String AUTOCONFIG_ENDPOINT = "/autoconfig";
    /**
     * The constant BEANS_ENDPOINT.
     */
    public static final String BEANS_ENDPOINT = "/beans";
    /**
     * The constant CONFIGPROPS_ENDPOINT.
     */
    public static final String CONFIGPROPS_ENDPOINT = "/configprops";
    /**
     * The constant ENV_ENDPOINT.
     */
    public static final String ENV_ENDPOINT = "/env";
    /**
     * The constant MAPPINGS_ENDPOINT.
     */
    public static final String MAPPINGS_ENDPOINT = "/mappings";
    /**
     * The constant METRICS_ENDPOINT.
     */
    public static final String METRICS_ENDPOINT = "/metrics";
    /**
     * The constant SHUTDOWN_ENDPOINT.
     */
    public static final String SHUTDOWN_ENDPOINT = "/shutdown";
    /**
     * The constant MSG_SUCCESS.
     */
    public static final String MSG_SUCCESS = "Success!";

    public static final String ANNOTATION_ROLE_USER = "hasAuthority('USER')";
   public static final String ANNOTATION_ROLE_NEWUSER = "hasAuthority('NEWUSER')";


  public enum TOPIC_NAME {
    FirstTopic,
    SecondTopic
  }

  public static final String FIRST_TOPIC = TOPIC_NAME.FirstTopic.name();
  public static final String SECOND_TOPIC = TOPIC_NAME.SecondTopic.name();
  public static final String FIRST_TOPIC_TEMPLATE_NAME = "firstTopicTemplateName";
  public static final String SECOND_TOPIC_TEMPLATE_NAME = "secondTopicTemplateName";
  public static final String DEFAULT_DISCRIMINATOR = "tutorial.default.tenant.discriminator";

  public static final String USERNAME_HEADER = "username";
  public static final String PASSWORD_HEADER = "password";
  public static final String TOKEN_HEADER = "token";

  public static final String DEFAULT_USER_EMAIL = "defaultuser@defaultadmin.com";
  public static final String DEFAULT_USER_FULLNAME = "Default User";
  public static final String DEFAULT_USER_PASSWORD = "default123";
  public static final String DEFAULT_USERNAME = "defaultuser";

  public static final String BASE_ENDPOINT = "/{tenant}";
  public static final String USER_ENDPOINT = BASE_ENDPOINT + "/user";
  public static final String REGISTRATION_ENDPOINT = BASE_ENDPOINT + "/registration";

  public static final String UNAUTHENTICATED_USER = "UnAuthenticated";

  public enum GRANT_TYPES {
    password,
    authorization_code,
    refresh_token,
    client_credentials
  }

  public static final int TENANT_HEADER_PRECEDENCE = Ordered.HIGHEST_PRECEDENCE;
  public static final int SEED_DATA_PRECEDENCE = TENANT_HEADER_PRECEDENCE - 1;
  public static final int TENANT_PRECEDENCE = SEED_DATA_PRECEDENCE - 1;
  public static final int USER_PRECEDENCE = Ordered.LOWEST_PRECEDENCE;
}