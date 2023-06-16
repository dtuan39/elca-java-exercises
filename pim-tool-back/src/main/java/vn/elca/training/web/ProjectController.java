package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/search")
    public List<ProjectDto> search() {
        return projectService.findAll()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/{substringName}") //localhost:8080/projects/search/EFV
    public List<ProjectDto> searchByName(@PathVariable("substringName") String substringName) {
        return projectService.findByNameContaining(substringName)
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search2") //localhost:8080/projects/search2?name=A
    public List<ProjectDto> searchByName2(@RequestParam("name") String substringName) {
        return projectService.findByNameContaining(substringName)
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public ProjectDto getProjectById(@PathVariable("id") Long id) {
        Project p = projectService.getProjectById(id);
        return mapper.projectToProjectDto(p);
    }

    @PostMapping("/update")
    public void updateProject(@RequestBody ProjectDto projectDto) {
        projectService.updateProject(projectDto.getId(), projectDto.getName(), projectDto.getFinishingDate());
    }
}
