package rob.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import rob.rest.controller.SecureController;

import java.util.Collections;

/**
 * @author Rob Benton
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .antMatcher(SecureController.PATH)
            // Use basic authentication
            .httpBasic()
            // Turn off session storage
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // All requests must be authenticated to the Secure controller
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return new InMemoryUserDetailsManager(
            new User(
                "user", "{noop}user",
                Collections.singletonList(new SimpleGrantedAuthority("userRole"))
            )
        );
    }
}
