package rob.rest.controller;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rob Benton
 */
@RestController
@RequestMapping(RootController.PATH)
public class RootController
{
    public static final String PATH = "/";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> get(HttpRequest httpRequest)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.OK);
        map.put("uri", httpRequest.getURI());
        map.put("timestamp", new Date());

        return ResponseEntity.ok(map);
    }
}
