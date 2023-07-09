package vn.elca.training.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROJECT_EMPLOYEE")
public class ProjectEmployee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @JsonIgnore
    private Project project;

    @ManyToOne()
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    @JsonIgnore
    private Employee employee;

    public ProjectEmployee(ProjectEmployeePK pk) {
        this.project = pk.getProject();
        this.employee = pk.getEmployee();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectEmployee)) return false;
        ProjectEmployee that = (ProjectEmployee) o;
        return Objects.equals(project, that.project) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, employee);
    }
}
