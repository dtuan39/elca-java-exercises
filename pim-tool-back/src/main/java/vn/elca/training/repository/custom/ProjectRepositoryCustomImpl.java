package vn.elca.training.repository.custom;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.entity.Status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findAllProject() {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .orderBy(QProject.project.projectNumber.asc())
                .fetch();
    }

    @Override
    public List<Project> findProjectByProjectNumber(int projectNumber) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetch();
    }

    @Override
    public List<Project> findProjectByAny(String value) {
        return new JPAQuery<Project>(em)
                .select(QProject.project)
                .from(QProject.project)
                .where(QProject.project.projectNumber.like("%" + value + "%")
                        .or(QProject.project.name.like("%" + value + "%"))
                        .or(QProject.project.customer.like("%" + value + "%"))
                        .or(QProject.project.status.eq(Status.valueOf(value))))
                .fetch();
    }

    @Override
    public boolean checkProjectNumber(int projectNumber) {
        return new JPAQuery<Project>(em)
                .select(QProject.project)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchFirst() != null;
    }

    @Override
    @Transactional
    public void updateProjectByProjectNumber(int projectNumber, Project project){
        JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
        queryFactory.update(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .set(QProject.project.projectNumber, project.getProjectNumber())
                .set(QProject.project.customer, project.getCustomer())
                .set(QProject.project.endDate, project.getEndDate())
                .set(QProject.project.name, project.getName())
                .set(QProject.project.startDate, project.getStartDate())
                .set(QProject.project.status, project.getStatus())
                .set(QProject.project.version, project.getVersion())
                .set(QProject.project.group, project.getGroup())
                .execute();
    }
}
