package vn.elca.training.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ProjectService projectService;

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
        // Find current project by id in database
        Project found = projectService.findById(projectDto.getId());

        if (found == null) {
            return ResponseEntity.notFound().build();
        } // not found

        // apply change
        found.setName(projectDto.getName());
        found.setFinishingDate(projectDto.getFinishingDate());

        // try to save to database using project service
        Project response = projectService.update(found);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        } // update fail

        return ResponseEntity.ok(mapper.projectToProjectDto(response));
    }
}
