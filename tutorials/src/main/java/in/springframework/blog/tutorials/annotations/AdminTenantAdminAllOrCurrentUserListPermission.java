package in.springframework.blog.tutorials.annotations;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN', 'USER')")
@PostFilter("hasAnyAuthority('ADMIN', 'TENANT_ADMIN') or (hasAuthority('USER') and filterObject.username == authentication.name)")
public @interface AdminTenantAdminAllOrCurrentUserListPermission {
}
