package pilotproject.Project_Management_ELCA.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.model.entity.Project;
import pilotproject.Project_Management_ELCA.service.impl.ProjectServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController extends AbstractApplicationController {
    private final ProjectServiceImpl projectServiceImpl;

    @Autowired
    public ProjectController(ProjectServiceImpl projectServiceImpl) {
        this.projectServiceImpl = projectServiceImpl;
    }

//    add but with send the response to client to check
    @PostMapping("/add")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectDto projectDto) {
        ProjectDto response = mapper.projectToProjectDto(projectServiceImpl.addProject(projectDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectServiceImpl.findAllProjects()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<ProjectDto> search(@RequestParam(required = false) String searchText, @RequestParam(required = false) String status) {
        return projectServiceImpl.searchProject(searchText, status)
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{number}")
    public ResponseEntity<ProjectDto> getProjectByNumber(@PathVariable int number) { //ResponseEntity chuyển obj về json để front end render dc ra màn hình
        ProjectDto projectDto = mapper.projectToProjectDto(projectServiceImpl.findProjectByNumber(number));
        return ResponseEntity.ok(projectDto);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<ProjectDto> deleteSingleProject(@RequestParam() Long id){
        projectServiceImpl.deleteSingleProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping ("/update")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto dto){
        ProjectDto updateProject = mapper.projectToProjectDto(projectServiceImpl.updateProject(dto));
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }
}
