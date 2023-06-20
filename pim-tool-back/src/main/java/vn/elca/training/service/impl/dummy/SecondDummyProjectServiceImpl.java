package vn.elca.training.service.impl.dummy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.util.List;

/**
 * @author gtn
 *
 */
@Service
@Profile("dummy")
public class SecondDummyProjectServiceImpl extends AbstractDummyProjectService implements ProjectService {

    @Override
    public List<Project> findAll() {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public long count() {
        printCurrentActiveProfiles();
        throw new UnsupportedOperationException("This is second dummy service");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByKeyword'");
    }
}
