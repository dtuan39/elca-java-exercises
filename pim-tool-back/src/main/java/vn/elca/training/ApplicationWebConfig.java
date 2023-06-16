package vn.elca.training;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;
import vn.elca.training.validator.TaskValidator;
import vn.elca.training.web.AbstractApplicationController;

/**
 * @author gtn
 */
@SpringBootApplication(scanBasePackages = "vn.elca.training")
@ComponentScan(basePackageClasses = {
        AbstractApplicationController.class,
        ApplicationMapper.class,
        ProjectService.class,
        TaskValidator.class
})
@PropertySource({"classpath:/application.properties", "classpath:/messages.properties"})
public class ApplicationWebConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWebConfig.class);
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/h2console/*");
        return registrationBean;
    }
}
