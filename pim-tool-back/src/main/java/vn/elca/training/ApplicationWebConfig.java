package vn.elca.training;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vn.elca.training.model.mapping.ProjectMapper;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;
import vn.elca.training.validator.ProjectValidator;
import vn.elca.training.web.AbstractApplicationController;

/**
 * @author gtn
 */
@SpringBootApplication(scanBasePackages = "vn.elca.training")
@ComponentScan(basePackageClasses = {AbstractApplicationController.class, ApplicationMapper.class, ProjectService.class, ProjectValidator.class, ProjectMapper.class})
@PropertySource({"classpath:/application.properties", "classpath:/messages.properties"})
@EnableJpaRepositories(basePackages = "vn.elca.training.repository")
public class ApplicationWebConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWebConfig.class);
    }

    @Bean
    public ServletRegistrationBean<WebServlet> h2servletRegistration() {
        return new ServletRegistrationBean<>(new WebServlet(), "/h2console/*");
    }
}
