package in.springframework.blog.tutorials;

import java.util.*;

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

}