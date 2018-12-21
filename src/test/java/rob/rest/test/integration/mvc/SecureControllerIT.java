package rob.rest.test.integration.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rob.rest.controller.SecureController;
import rob.rest.service.ExtraService;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

/**
 * SecureControllerIT tests the MVC endpoints for the {@link SecureController}
 * controller. It does not include security testing since that is handled on
 * the service layer.
 *
 * @author Rob Benton
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SecureController.class, excludeAutoConfiguration = MockMvcSecurityAutoConfiguration.class)
public class SecureControllerIT
{
    private static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExtraService extraService;

    @BeforeClass
    public static void beforeClass()
    {
        objectMapper = new ObjectMapper();
    }

    /**
     * Test the the secure endpoint returns the correct object type.
     */
    @Test
    public void testSecure() throws Exception
    {
        mockMvc
            .perform(
                get(SecureController.PATH).accept(MediaType.APPLICATION_JSON)
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
     * Test that the extra endpoint returns the appropriate type.
     */
    @Test
    public void testExtra() throws Exception
    {
        final String path = String.format(
            "%s/%s", SecureController.PATH, SecureController.EXTRA_PATH
        );
        mockMvc
            .perform(
                get(path).accept(MediaType.APPLICATION_JSON)
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
     * SecureControllerResponse defines a concrete type for the return value
     * from the {@link SecureController}. Jackson needs concrete types for
     * deserialization and parameterized types, such as Map are not suitable
     * for the {@link ObjectMapper#readValue(String, Class)} method.
     */
    private static final class SecureControllerResponse extends HashMap<String, Object>
    {
    }
}
