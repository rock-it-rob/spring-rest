package rob.rest.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * SecureAspect is an aspect class that defines pointcuts for requests sent to
 * the {@link rob.rest.controller.SecureController}.
 *
 * @author Rob Benton
 */
@Aspect
@Component
public class SecureAspect
{
    private static final Logger log = LoggerFactory.getLogger(SecureAspect.class);

    @Pointcut("within(rob.rest.controller.SecureController) && @annotation(Logging)")
    private void loggingPointcut()
    {
    }

    @Before("loggingPointcut()")
    public void logSecureControllerBefore()
    {
        log.info("Before secure controller.");
    }
}