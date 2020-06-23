package in.springframework.blog.tutorials.utils;

import in.springframework.blog.tutorials.entities.Tenant;
import in.springframework.blog.tutorials.entities.User;

public class RequestContext {

  public static final ThreadLocal<Tenant> currentTenant = new ThreadLocal<>();
  public static final ThreadLocal<User> currentUser = new ThreadLocal<>();
  public  static final ThreadLocal<String> currentTenantDiscriminator = new ThreadLocal<>();
}
