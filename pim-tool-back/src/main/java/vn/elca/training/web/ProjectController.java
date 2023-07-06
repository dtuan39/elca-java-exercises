package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.util.List;

/**
 * @author gtn
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    @Autowired
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/welcome")
    public String welcomepage() {
        return "Welcome to ELCA Bootcamp";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProject() {
        List<Project> projects = projectService.findAllProject();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/find/{projectNumber}")
    public ResponseEntity <List<Project>> getProjectByProjectNumber(@PathVariable("projectNumber") int projectNumber) {
        List <Project> projects = projectService.findProjectByProjectNumber(projectNumber);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        Project addProject = projectService.addProject(project);
        return new ResponseEntity<>(addProject, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        Project updateProject = projectService.updateProject(project);
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/{value}")
    public ResponseEntity <List<Project>> searchPage(@PathVariable("value") String value){
        List<Project> projects = projectService.findProjectByAny(value);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
