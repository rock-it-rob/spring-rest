package rob.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AppConfig is the entry point for the application.
 *
 * @author Rob Benton
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class AppConfig
{
    public static void main(String[] args)
    {
        SpringApplication.run(AppConfig.class, args);
    }
}
