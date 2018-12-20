package rob.rest.test.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rob.rest.controller.RootController;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * RootControllerIT is a test suite for the {@link rob.rest.controller.RootController}
 * MVC controller.
 *
 * @author Rob Benton
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RootControllerIT
{
    private static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void beforeClass()
    {
        objectMapper = new ObjectMapper();
    }

    /**
     * Test that the root controller is reachable and returns a 200 status.
     */
    @Test
    public void testRootController() throws Exception
    {
        final MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.get(RootController.PATH)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        assertNotNull(mvcResult);
        final String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertNotEquals(0, result.length());
        final RootControllerResponse response = objectMapper.readValue(result, RootControllerResponse.class);
        assertNotNull(response);
        assertNotEquals(0, response.size());
    }

    private static class RootControllerResponse extends HashMap<String, Object>
    {
    }
}
