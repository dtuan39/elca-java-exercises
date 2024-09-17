package pilotproject.Project_Management_ELCA.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilotproject.Project_Management_ELCA.model.entity.Employee;
import pilotproject.Project_Management_ELCA.repository.EmployeeRepository;
import pilotproject.Project_Management_ELCA.service.EmployeeService;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findEmployeesByVisaOrName(String value) {
        return employeeRepository.findEmployeesByVisaOrName(value);
    }
}
