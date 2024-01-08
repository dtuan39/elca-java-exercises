package pilotproject.Project_Management_ELCA.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import pilotproject.Project_Management_ELCA.model.entity.Project;
import pilotproject.Project_Management_ELCA.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Project findProjectByNumber(Integer number) {
        return new JPAQuery<Project>(em).from(QProject.project)
                .where(QProject.project.projectNumber.eq(number))
                .fetchOne();
    }

    @Override
    public List<Project> findProjectBySearchTextAndStatus(String searchText, String status) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (!"".equals(searchText)) {
            predicate.or(QProject.project.projectNumber.stringValue().containsIgnoreCase(searchText));
            predicate.or(QProject.project.name.containsIgnoreCase(searchText));
            predicate.or(QProject.project.customer.containsIgnoreCase(searchText));
        }

        if (!"".equals(status)) {
            try {
                predicate.and(QProject.project.status.eq(Project.Status.valueOf(status)));
            } catch (IllegalArgumentException e) {
                // Handle the case when the provided status is not a valid enum constant
            }
        }
        JPAQuery<Project> query = new JPAQuery<>(em);
        return query.select(QProject.project)
                .from(QProject.project)
                .where(predicate)
                .orderBy(QProject.project.projectNumber.asc())
                .fetch();
    }

    @Override
    public List<Project> getProjectsPagination(int limit, int skip) {
        JPAQuery<Project> query = new JPAQuery<>(em);
        return query.select(QProject.project)
                .from(QProject.project)
                .orderBy(QProject.project.projectNumber.asc())
                .offset(skip)
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Project> searchProjectPagination(String searchText, String status, int limit, int skip) {

        BooleanBuilder predicate = new BooleanBuilder();

        if (!"".equals(searchText)) {
            predicate.or(QProject.project.projectNumber.stringValue().containsIgnoreCase(searchText));
            predicate.or(QProject.project.name.containsIgnoreCase(searchText));
            predicate.or(QProject.project.customer.containsIgnoreCase(searchText));
        }

        if (!"".equals(status)) {
            try {
                predicate.and(QProject.project.status.eq(Project.Status.valueOf(status)));
            } catch (IllegalArgumentException e) {
                // Handle the case when the provided status is not a valid enum constant
            }
        }
        JPAQuery<Project> query = new JPAQuery<>(em);
        return query.select(QProject.project)
                .from(QProject.project)
                .where(predicate)
                .orderBy(QProject.project.projectNumber.asc())
                .offset(skip)
                .limit(limit)
                .fetch();
    }
}
