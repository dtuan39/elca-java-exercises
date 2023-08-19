package vn.elca.training;

import org.springframework.boot.SpringApplication;

/**
 * @author vlp
 *
 */
public class ApplicationLauncher {
    public static void main(String[] args) {
        Class<?>[] configs = {ResourceServerConfig.class, ApplicationWebConfig.class};
        SpringApplication.run(configs, args);
    }

}
