package vn.elca.training.repository.projectcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.entity.Project;

import java.util.List;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/projects")
public class ProjectController{

    private final ProjectRepository_Impl projectService;

    public ProjectController(ProjectRepository_Impl projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/welcome")
public String welcomepage() {
    return "Welcome to ELCA Bootcamp";
}
    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProject(){
        List<Project> projects = projectService.findAllProject();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Project> getProjectByid(@PathVariable("id") Long id){
        Project projects = projectService.findProjectByid(id);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Project> addProject(@RequestBody Project project){
        Project addProject = projectService.addProject(project);
        return new ResponseEntity<>(addProject,HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Project> updateProject(@RequestBody Project project){
        Project updateProject = projectService.updateProject(project);
        return new ResponseEntity<>(updateProject,HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id){
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
