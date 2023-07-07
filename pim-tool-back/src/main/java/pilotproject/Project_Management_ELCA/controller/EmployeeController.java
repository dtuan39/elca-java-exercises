package pilotproject.Project_Management_ELCA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pilotproject.Project_Management_ELCA.model.dto.EmployeeDto;
import pilotproject.Project_Management_ELCA.model.dto.GroupDto;
import pilotproject.Project_Management_ELCA.service.impl.EmployeeServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController extends AbstractApplicationController {
    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.findAllEmployees()
                .stream()
                .map(mapper::employeeToEmployeeDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
