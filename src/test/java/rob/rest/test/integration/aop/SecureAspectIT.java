package rob.rest.test.integration.aop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import rob.rest.aspect.SecureAspect;
import rob.rest.controller.SecureController;
import rob.rest.service.ExtraService;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

/**
 * SecureAspectIT tests the functionality of the {@link rob.rest.aspect.SecureAspect}
 * aspect class.
 *
 * @author Rob Benton
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecureController.class, SecureAspectIT.SecureAspectTest.class})
@EnableAspectJAutoProxy
public class SecureAspectIT
{
    private static boolean called = false;

    @MockBean
    private ExtraService extraService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SecureController secureController;

    @Before
    public void before()
    {
        called = false;
    }

    /**
     * Test that the pointcuts declared for the {@link SecureAspect#logSecureController()}
     * correctly match.
     */
    @Test
    public void testLogSecureController()
    {
        assertFalse(called);
        secureController.get();
        assertTrue(called);
    }

    /**
     * Test that the pointcut declared for {@link SecureAspect#logSecureControllerRequest(HttpServletRequest)}
     * correctly matches.
     */
    @Test
    public void testLogRequest() throws Exception
    {
        assertFalse(called);
        secureController.getExtra(httpServletRequest);
        assertTrue(called);
    }


    /**
     * SecureAspectTest is used for testing the AspectJ advice methods of
     * {@link SecureAspect}. It overrides the interesting methods to verify
     * they have been called.
     */
    static class SecureAspectTest extends SecureAspect
    {
        @Override
        public void logSecureController()
        {
            called = true;
        }

        @Override
        public void logSecureControllerRequest(HttpServletRequest request)
        {
            called = true;
        }
    }
}
