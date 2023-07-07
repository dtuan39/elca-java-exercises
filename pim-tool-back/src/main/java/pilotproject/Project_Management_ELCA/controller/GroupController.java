package pilotproject.Project_Management_ELCA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pilotproject.Project_Management_ELCA.model.dto.GroupDto;
import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.service.impl.GroupServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController extends AbstractApplicationController{

    private final GroupServiceImpl groupServiceImpl;

    @Autowired
    public GroupController(GroupServiceImpl groupServiceImpl) {
        this.groupServiceImpl = groupServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupDto>> getAllGroups(){
        List<GroupDto> groups = groupServiceImpl.findAllGroups()
                .stream()
                .map(mapper::groupToGroupDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
}
