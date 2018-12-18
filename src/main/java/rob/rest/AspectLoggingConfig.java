package rob.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import rob.rest.aspect.ExtraServiceAspect;
import rob.rest.aspect.SecureAspect;

/**
 * AspectLoggingConfig is a spring configuration class activated by the
 * {@link #PROFILE} spring profile. It turns on the beans that provide logging
 * by aop advisor methods.
 *
 * @author Rob Benton
 */
@Configuration
@Profile(AspectLoggingConfig.PROFILE)
public class AspectLoggingConfig
{
    public static final String PROFILE = "aspectlogging";

    @Bean
    public ExtraServiceAspect extraServiceAspect()
    {
        return new ExtraServiceAspect();
    }

    @Bean
    public SecureAspect secureAspect()
    {
        return new SecureAspect();
    }
}
