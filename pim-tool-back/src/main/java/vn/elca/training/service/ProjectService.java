package vn.elca.training.service;

import vn.elca.training.model.entity.Project;

import java.time.LocalDate;
import java.util.List;

/**
 * @author vlp
 */
public interface ProjectService {
    List<Project> findAll();

    long count();

    List<Project> findByNameContaining(String substringName);

    Project getProjectById(Long id);

    void updateProject(Long id, String name, LocalDate date);
}
