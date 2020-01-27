package org.n52.oidcspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * Disabling spring default basic authentication with the exclusions.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class})
public class SpringKeycloakApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKeycloakApplication.class, args);
    }
}
