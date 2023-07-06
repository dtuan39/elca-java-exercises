package vn.elca.training.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * This class is used to implement the custom query for ProjectRepository
 * @see vn.elca.training.repository.ProjectRepository
 * @see vn.elca.training.repository.custom.ProjectRepositoryCustom
 * @author thomas.dang
 */
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findProjectByConditions(BooleanExpression predicate, int page, int limit) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(predicate)
                .orderBy(QProject.project.projectNumber.asc())
                .offset((long) page * limit)
                .limit(limit)
                .fetch();
    }

    @Override
    public Optional<Project> findProjectByNumberLoadMembers(Integer number) {
        return Optional.ofNullable(new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(number))
                .fetchOne());
    }
}
