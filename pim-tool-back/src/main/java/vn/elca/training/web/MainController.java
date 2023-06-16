package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.service.ProjectService;

/**
 * @author vlp
 */
@RestController
public class MainController extends AbstractApplicationController {

    private final ProjectService projectService;
    private String title;
    @Value("${application.message}")
    private String message;

    public MainController(ProjectService theProjectService) {
        this.projectService = theProjectService;
    }

    @GetMapping("/main")
    public String main() {
        return title + ". " + String.format(message, projectService.count());
    }
}
