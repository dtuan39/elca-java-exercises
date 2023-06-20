package vn.elca.training.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.elca.training.exception.ProjectNotFoundException;
import vn.elca.training.exception.UpdateProjectException;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

/**
 * @author thomas.dang
 * 
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends AbstractApplicationController {
    @Autowired
    @Qualifier("firstDummyProjectServiceImpl")
    private ProjectService projectService;
    private Project response;
    private Log logger = LogFactory.getLog(this.getClass());

    @GetMapping("/id/{id}")
    public ResponseEntity<ProjectDto> searchById(@PathVariable long id) {
        logger.info("using " + projectService.getClass().getName());
        try {
            response = projectService.findById(id);
        } catch (ProjectNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.projectToProjectDto(response));
    }

    @PutMapping("/update")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto) {
        try {
            response = projectService.update(projectDto);
        } catch (ProjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateProjectException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mapper.projectToProjectDto(response));
    }
}
