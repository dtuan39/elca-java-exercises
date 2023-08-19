package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.elca.training.model.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {
    //find by employee visa
    Optional<Employee> findByVisa(String visa);

    //find employees by visa
    Set<Employee> findByVisaIn(List<String> visas);

    //if at least one visa is not found, return false, ensure all visas are found
    boolean existsByVisaIn(List<String> visas);

    //find employees by visa containing keyword
    List<Employee> findByVisaContaining(String keyword);
}
