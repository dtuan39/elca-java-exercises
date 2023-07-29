package pilotproject.Project_Management_ELCA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pilotproject.Project_Management_ELCA.model.entity.Employee;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Set<Employee> findByVisaIn(List<String> visas);
}
