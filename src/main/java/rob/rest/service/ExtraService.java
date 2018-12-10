package rob.rest.service;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;

/**
 * @author Rob Benton
 */
public interface ExtraService
{
    @PreAuthorize("hasRole('userRole')")
    Map<String, Object> extra();
}
