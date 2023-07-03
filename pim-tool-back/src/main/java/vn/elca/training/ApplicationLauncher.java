package vn.elca.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author vlp
 */
public class ApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWebConfig.class, args);
    }
}
