package vn.elca.training.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDTO;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ProjectInfoException;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects")
@Validated
public class ProjectController {

    private final ProjectService projectService;
    private final ApplicationMapper mapper;

    @Autowired
    public ProjectController(ProjectService projectService, ApplicationMapper mapper) {
        this.projectService = projectService;
        this.mapper = mapper;
    }

    @GetMapping("/all/{offset}")
    public ResponseEntity<Page<ProjectDTO>> getProjects(@PathVariable int offset) {
        Page<ProjectDTO> result = this.projectService.getProjectWithPagination(offset)
                .map(mapper::projectToProjectDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{projectNumber}")
    public ResponseEntity<ProjectDTO> getProjectByProjectNumber(@PathVariable Integer projectNumber) {
        ProjectDTO dto = this.projectService.getProjectByProjectNumber(projectNumber);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProjectDTO>> searchProjects
            (@RequestParam(required = false) String searchValue,
             @RequestParam(required = false) String statusValue,
             @RequestParam(required = false, defaultValue = "0") int offset) {
        Page<ProjectDTO> result = this.projectService.searchProjectWithPagination(searchValue, statusValue, offset)
                .map(mapper::projectToProjectDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectDTO> addProject(@RequestBody @Valid ProjectDTO dto) throws ProjectInfoException {
        Project newProject = this.projectService.addProject(dto);
        ProjectDTO newProjectDTO = mapper.projectToProjectDto(newProject);
        return new ResponseEntity<ProjectDTO>(newProjectDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody @Valid ProjectDTO dto) throws ProjectInfoException {
        Project newProject = this.projectService.updateProject(dto);
        ProjectDTO newProjectDTO = mapper.projectToProjectDto(newProject);
        return new ResponseEntity<ProjectDTO>(newProjectDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public void deleteProjects(@RequestBody List<Integer> projectNumbers) {
        this.projectService.deleteProjects(projectNumbers);
    }

}
