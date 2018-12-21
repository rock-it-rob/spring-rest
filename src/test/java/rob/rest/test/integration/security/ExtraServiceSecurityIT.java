package rob.rest.test.integration.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import rob.rest.service.ExtraService;

import static org.mockito.Mockito.*;

/**
 * ExtraServiceSecurityIT is a test suite for the security constraints on
 * the interface methods of {@link rob.rest.service.ExtraService}. The actual
 * object used is a mock implementation.
 * @author Rob Benton
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ExtraServiceSecurityIT
{
    @Configuration
    static class Config
    {
        // IMPORTANT!
        // For some reason I can't get the @MockBean annotation to work with
        // security. But mocking the bean programmatically and returning it
        // from a bean annotated method seems to work.
        @Bean
        public ExtraService extraService() { return mock(ExtraService.class); }
    }

    @Autowired
    private ExtraService extraService;

    /**
     * Test that an unauthenticated request to the ExtraService fails.
     */
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testNoAuth()
    {
        extraService.extra();
    }

    /**
     * Test that an authenticated call to the ExtraService succeeds.
     */
    @Test
    @SecurityTestUser
    public void testAuth()
    {
        extraService.extra();
    }
}
