package pilotproject.Project_Management_ELCA.repository.custom;

import pilotproject.Project_Management_ELCA.model.entity.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findEmployeesByVisaOrName(String value);
}
