package rob.rest.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rob Benton
 */
@Service
public class DefaultExtraService implements ExtraService
{
    @Override
    public Map<String, Object> extra()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("extra", "Have a nice day!");
        return map;
    }
}
