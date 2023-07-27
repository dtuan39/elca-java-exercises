package vn.elca.training;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vn.elca.training.model.mapping.GroupMapper;
import vn.elca.training.model.mapping.ProjectMapper;
import vn.elca.training.service.ProjectService;
import vn.elca.training.validator.ProjectValidator;
import vn.elca.training.web.AbstractApplicationController;

/**
 * @author gtn
 */
@SpringBootApplication(scanBasePackages = "vn.elca.training")
@ComponentScan(basePackageClasses = {AbstractApplicationController.class, ProjectService.class, ProjectValidator.class, ProjectMapper.class, GroupMapper.class})
@PropertySource({"classpath:/application.properties", "classpath:/messages.properties"})
@EnableJpaRepositories(basePackages = "vn.elca.training.repository")
public class ApplicationWebConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWebConfig.class);
    }
}
