package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.ProjectEmployee;

import java.util.List;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long>, QuerydslPredicateExecutor<ProjectEmployee> {
    List<Employee> getEmployeesByProjectId(Long id);

    void deleteByProjectId(Long id);

    List<ProjectEmployee> findAllByProjectId(Long id);

}
