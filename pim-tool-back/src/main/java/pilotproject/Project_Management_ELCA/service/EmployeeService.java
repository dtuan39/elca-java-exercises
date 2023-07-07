package pilotproject.Project_Management_ELCA.service;

import org.springframework.stereotype.Service;
import pilotproject.Project_Management_ELCA.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAllEmployees();
}
