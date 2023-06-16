package vn.elca.training.service.impl.dummy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gtn
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
    public List<Project> findByNameContaining(String substringName) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public Project getProjectById(Long id) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public void updateProject(Long id, String name, LocalDate date) {
        throw new UnsupportedOperationException("This is second dummy service");
    }
}
