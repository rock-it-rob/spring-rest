package rob.rest.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rob.rest.AspectLoggingConfig;
import rob.rest.controller.SecureController;

import javax.servlet.http.HttpServletRequest;

/**
 * SecureAspect is an aspect class that defines pointcuts for requests sent to
 * the {@link rob.rest.controller.SecureController}.
 *
 * @author Rob Benton
 */
@Aspect
@Component
@Profile(AspectLoggingConfig.PROFILE)
public class SecureAspect
{
    private static final Logger log = LoggerFactory.getLogger(SecureAspect.class);

    @Pointcut(value = "within(rob.rest.controller.SecureController)")
    private void secureControllerPointcut()
    {
    }

    @Pointcut("secureControllerPointcut() && @annotation(Logging)")
    private void logPointcut()
    {
    }

    @Pointcut("logPointcut() && !args(javax.servlet.http.HttpServletRequest)")
    private void logWithoutRequest()
    {
    }

    @Pointcut("logPointcut() && args(javax.servlet.http.HttpServletRequest) && args(httpServletRequest)")
    private void logWithRequest(HttpServletRequest httpServletRequest)
    {
    }

    /**
     * Logs the executions of any methods in the {@link SecureController}
     * class that are annotated with {@link Logging} and do not have a
     * {@link HttpServletRequest} as their first parameter.
     */
    @Before("logWithoutRequest()")
    public void logSecureController()
    {
        log.info("Logging without request object.");
    }

    /**
     * Logs the executions of any methods in the {@link SecureController} class
     * that are annotated with {@link Logging} and have a
     * {@link HttpServletRequest} as their first parameter.
     *
     * @param httpServletRequest HttpServletRequest
     */
    @Before("logWithRequest(httpServletRequest)")
    public void logSecureControllerRequest(HttpServletRequest httpServletRequest)
    {
        log.info("Servlet request: " + httpServletRequest.toString());
    }
}
