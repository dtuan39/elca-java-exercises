package pilotproject.Project_Management_ELCA.repository.custom;

import com.querydsl.core.BooleanBuilder;
import pilotproject.Project_Management_ELCA.model.entity.Project;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom {
    Project findProjectByNumber(Integer number);

    List<Project> findProjectBySearchTextAndStatus(String searchText, String status);

    List<Project> getProjectsPagination(int limit, int skip);

    List<Project> searchProjectPagination(String searchText, String status, int limit, int skip);
}
