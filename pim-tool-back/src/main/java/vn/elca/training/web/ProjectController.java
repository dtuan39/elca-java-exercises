package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 * @author thomas.dang
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/search")
    public List<ProjectDto> search(@RequestParam String keyword) {
        if (keyword.isBlank()) {
            return new ArrayList<>();
        }
        return projectService.findAll()
                .stream()
                .filter(product -> product.getName().trim().toLowerCase()
                        .contains(keyword.trim().toLowerCase()))
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public List<ProjectDto> searchById(@PathVariable long id) {
        return projectService.findAll()
                .stream()
                .filter(project -> {
                    return project.getId() == id;
                })
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto) {
        //Find current project by id in database
        Project found = projectService.findById(projectDto.getId());

        if(found == null) {
            return ResponseEntity.notFound().build();
        }//not found

        //apply change
        found.setName(projectDto.getName());
        found.setFinishingDate(projectDto.getFinishingDate());
        
        //try to save to database using project service 
        Project response = projectService.update(found);

        if(response == null) {
            return ResponseEntity.badRequest().build();
        }//update fail

        return ResponseEntity.ok(mapper.projectToProjectDto(response));
    }
}
