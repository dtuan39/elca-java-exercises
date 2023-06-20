package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.List;

/**
 * @author vlp
 *
 */
@Service
@Profile("!dummy | dev")
@Primary
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public Project findById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Project update(ProjectDto project) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Project> findByKeyword(String keyword) {
        return projectRepository.findByNameContainingIgnoreCase(keyword);
    }

}
