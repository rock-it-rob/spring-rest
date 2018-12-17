package rob.rest.aspect;

import java.lang.annotation.*;

/**
 * Logging is an annotation for targetting method execution that should be
 * logged. It is interpreted by AspectJ pointcut expressions.
 *
 * @author Rob Benton
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logging
{
}
