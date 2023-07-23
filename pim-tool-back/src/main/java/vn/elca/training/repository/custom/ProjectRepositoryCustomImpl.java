package vn.elca.training.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * This class is used to implement the custom query for ProjectRepository
 *
 * @author thomas.dang
 * @see vn.elca.training.repository.ProjectRepository
 * @see vn.elca.training.repository.custom.ProjectRepositoryCustom
 */
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Project> findProjectByKeywordAndStatusSortByProjectNumber(String keyword, Project.Status status, Pageable pageable) {
        BooleanExpression conditions = status == null ? null : QProject.project.status.eq(status);
        // keyword can be projects' name or number or customer or both of three or all of them separated by comma
        String[] parts = keyword.split(",");
        for (String part : parts) { // build the conditions
            if (part.isEmpty()) continue;
            conditions = QProject.project.projectNumber.stringValue().containsIgnoreCase(part)
                    .or(QProject.project.name.containsIgnoreCase(part))
                    .or(QProject.project.customer.containsIgnoreCase(part));
        }
        JPAQuery<Project> query = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(conditions)
                .orderBy(QProject.project.projectNumber.asc());

        long totalCount = query.fetchCount();
        List<Project> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> totalCount);
    }
}
