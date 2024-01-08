package pilotproject.Project_Management_ELCA.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import pilotproject.Project_Management_ELCA.model.entity.Employee;
import pilotproject.Project_Management_ELCA.model.entity.QEmployee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom{
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Employee> findEmployeesByVisaOrName(String value) {
        String realValue = value.trim();
        BooleanExpression predicate = QEmployee.employee.visa.containsIgnoreCase(realValue)
                .or(QEmployee.employee.firstName.containsIgnoreCase(realValue))
                .or(QEmployee.employee.lastName.containsIgnoreCase(realValue));

        JPAQuery<Employee> query = new JPAQuery<>(em);
        return query.select(QEmployee.employee)
                .from(QEmployee.employee)
                .where(predicate)
                .orderBy(QEmployee.employee.lastName.asc())
                .fetch();
    }
}
