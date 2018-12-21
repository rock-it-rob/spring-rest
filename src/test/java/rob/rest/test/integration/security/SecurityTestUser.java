package rob.rest.test.integration.security;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * SecurityTestUser is a meta-annotation used to supply a test user to methods
 * that need an authenticated user.
 *
 * @author Rob Benton
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(
    username = SecurityTestUser.USERNAME,
    authorities = SecurityTestUser.ROLE
)
public @interface SecurityTestUser
{
    String USERNAME = "user";
    String ROLE = "ROLE_userRole";
}
