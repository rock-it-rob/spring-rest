package rob.rest.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rob.rest.service.ExtraService;

/**
 * ExtraServiceAspect provides aop advice for {@link rob.rest.service.ExtraService}
 * implementations.
 *
 * @author Rob Benton
 */
@Component
@Aspect
public class ExtraServiceAspect
{
    private static final Logger log = LoggerFactory.getLogger(ExtraServiceAspect.class);

    /**
     * Pointcut that identifies executions in an implementation of
     * {@link ExtraService}.
     *
     * @param extraService ExtraService
     */
    @Pointcut("this(rob.rest.service.ExtraService) && this(extraService)")
    private void extraService(ExtraService extraService)
    {
    }

    @Before("extraService(e)")
    public void beforeExtraService(ExtraService e)
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info(String.format(
            "Call to ExtraService implementation %s by user %s",
            e.getClass().getCanonicalName(), userDetails.getUsername()
        ));
    }
}
