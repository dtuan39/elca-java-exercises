package pilotproject.Project_Management_ELCA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.model.dto.ProjectMembersDto;
import pilotproject.Project_Management_ELCA.model.entity.Project;
import pilotproject.Project_Management_ELCA.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController extends AbstractApplicationController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectMembersDto projectMembersDto) {
        Project project = projectService.createProject(projectMembersDto);
        ProjectDto response = mapper.projectToProjectDto(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectDto projectDto) {
        ProjectDto response = mapper.projectToProjectDto(projectService.addProject(projectDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.findAllProjects()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<ProjectDto> search(@RequestParam(required = false) String searchText, @RequestParam(required = false) String status) {
        return projectService.searchProject(searchText, status)
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{number}")
    public ResponseEntity<ProjectDto> getProjectByNumber(@PathVariable int number) { //ResponseEntity chuyển obj về json để front end render dc ra màn hình
        ProjectDto projectDto = mapper.projectToProjectDto(projectService.findProjectByNumber(number));
        return ResponseEntity.ok(projectDto);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<ProjectDto> deleteSingleProject(@RequestParam() Long id){
        projectService.deleteSingleProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping ("/update")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto dto){
        ProjectDto updateProject = mapper.projectToProjectDto(projectService.updateProject(dto));
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }
}
