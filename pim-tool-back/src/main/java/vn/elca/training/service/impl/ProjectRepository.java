package vn.elca.training.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Project;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {
    void deleteProjectById(Long id);

    Optional<Project> findProjectById(Long id);

}
