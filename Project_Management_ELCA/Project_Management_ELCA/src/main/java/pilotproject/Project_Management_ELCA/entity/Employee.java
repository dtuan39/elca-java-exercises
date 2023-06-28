package pilotproject.Project_Management_ELCA.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author gtn
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String visa;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private Date birth_date;

    @Column(nullable = true)
    private int version;

    @OneToOne(mappedBy = "employee")
    private Group group;

    @ManyToMany(mappedBy = "employeeList")
    private Set<Project> projectList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return version == employee.version && Objects.equals(id, employee.id) && Objects.equals(visa, employee.visa) && Objects.equals(first_name, employee.first_name) && Objects.equals(last_name, employee.last_name) && Objects.equals(birth_date, employee.birth_date) && Objects.equals(group, employee.group) && Objects.equals(projectList, employee.projectList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, visa, first_name, last_name, birth_date, version, group, projectList);
    }
}
