package in.springframework.blog.tutorials;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SanjnanConstants {


    public final static char[] ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    /**
     * The constant deviceCapability.
     */
    public static  final TreeMap<String,List<String>> deviceCapability = new TreeMap<>();

    /**
     * The constant VERSION_1.
     */
    public static final String VERSION_1 = "/v1";

    /**
     * The constant EXCEPTION_URL.
     */
    public static final String EXCEPTION_URL = "http://sanjnan.com/errors/%s";
    /**
     * The constant DATE_PATTERN.
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * The constant DATE_PATTERN_CREATE_EVENT.
     */

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

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SanjnanConstants.class);

    public static final String ANNOTATION_ROLE_USER = "hasAuthority('USER')"; // 0

}