package vn.elca.training.service;

import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.exception.EmployeeNotFoundException;

import java.util.List;


public interface EmployeeService {

    List<Employee> getAllById(List<Long> ids) throws EmployeeNotFoundException;
}
