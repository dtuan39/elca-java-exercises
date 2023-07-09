package vn.elca.training.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.exception.EmployeeNotFoundException;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> getAllById(List<Long> ids) throws EmployeeNotFoundException {
        List<Employee> result = new ArrayList<>();
        EmployeeNotFoundException empException = new EmployeeNotFoundException();

        for (Long id : ids) {
            Employee e = employeeRepo.findById(id).orElse(null);
            if (e == null) {
                empException.add(id);
            } else {
                result.add(e);
            }
        }
        if (empException.getErrors().size() > 0) {
            throw empException;
        }
        return result;
    }
}
