package rob.rest.test.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rob.rest.controller.SecureController;

import java.util.Base64;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

/**
 * @author Rob Benton
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(SecureControllerIT.TEST_PROPERTIES)
public class SecureControllerIT
{
    static final String TEST_PROPERTIES = "classpath:credentials.properties";

    private static ObjectMapper objectMapper;

    private String authorization;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void beforeClass()
    {
        objectMapper = new ObjectMapper();
    }

    @Before
    public void before()
    {
        final String raw = String.format("%s:%s", username, password);
        final Base64.Encoder encoder = Base64.getMimeEncoder();
        authorization = String.format(
            "Basic %s", encoder.encodeToString(raw.getBytes())
        );
    }

    /**
     * Test that the secure endpoint is not accessible without credentials.
     */
    @Test
    public void testUnauthenticated() throws Exception
    {
        mockMvc.perform(get(SecureController.PATH))
            .andExpect(status().isUnauthorized());
    }

    /**
     * Test the the secure endpoint is accessible with credentials.
     */
    @Test
    public void testAuthenticated() throws Exception
    {
        mockMvc
            .perform(
                get(SecureController.PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, authorization)
            )
            .andExpect(status().isOk())
            .andDo(mvcResult -> {
                assertNotNull(mvcResult);
                final String result = mvcResult.getResponse().getContentAsString();
                assertNotEquals(0, result.length());
                SecureControllerResponse response = objectMapper.readValue(result, SecureControllerResponse.class);
                assertNotNull(response);
                assertNotEquals(0, response.size());
            });
    }

    /**
     * Test that the extra endpoint is not accessible without credentials.
     */
    @Test
    public void testUnauthenticatedExtra() throws Exception
    {
        final String path = String.format(
            "%s/%s", SecureController.PATH, SecureController.EXTRA_PATH
        );
        mockMvc.perform(get(path))
            .andExpect(status().isUnauthorized());
    }

    /**
     * Test that the extra endpoint is accessible with credentials.
     */
    @Test
    public void testAuthenticatedExtra() throws Exception
    {
        final String path = String.format(
            "%s/%s", SecureController.PATH, SecureController.EXTRA_PATH
        );
        mockMvc
            .perform(
                get(path)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, authorization)
            )
            .andExpect(status().isOk())
            .andDo(mvcResult -> {
                assertNotNull(mvcResult);
                final String result = mvcResult.getResponse().getContentAsString();
                assertNotEquals(0, result.length());
                SecureControllerResponse response = objectMapper.readValue(result, SecureControllerResponse.class);
                assertNotNull(response);
                assertNotEquals(0, response.size());
            });
    }


    private static final class SecureControllerResponse extends HashMap<String, Object>
    {
    }
}
