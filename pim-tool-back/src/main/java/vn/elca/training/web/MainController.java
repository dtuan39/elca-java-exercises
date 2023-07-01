package vn.elca.training.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.service.ProjectService;

/**
 * @author vlp
 */
@RestController
public class MainController extends AbstractApplicationController {
    private ProjectService projectService;

    public MainController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/main")
    public String welcome() {
        return projectService.welcome();
    }
}
