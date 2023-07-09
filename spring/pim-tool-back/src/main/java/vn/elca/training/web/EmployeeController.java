package vn.elca.training.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.exception.EmployeeNotFoundException;
import vn.elca.training.service.EmployeeService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/get")
    public List<Employee> getEmployeesById(@RequestParam(name = "ids") String idString) throws EmployeeNotFoundException {
        List<Long> ids = Arrays.stream(idString.split(",")).map((id) -> Long.parseLong(id)).collect(Collectors.toList());
        return this.employeeService.getAllById(ids);
    }
}
