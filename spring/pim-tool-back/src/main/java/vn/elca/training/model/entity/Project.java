package vn.elca.training.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.elca.training.enumaration.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author vlp
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19, updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private Group groupId;

    @Column(name = "PROJECT_NUMBER", unique = true, length = 4, nullable = false)
    private int projectNumber;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "CUSTOMER", length = 50, nullable = false)
    private String customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 3, nullable = false)
    private Status status;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProjectEmployee> projectEmployees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return projectNumber == project.projectNumber && Objects.equals(id, project.id) && Objects.equals(groupId, project.groupId) && Objects.equals(name, project.name) && Objects.equals(customer, project.customer) && status == project.status && Objects.equals(startDate, project.startDate) && Objects.equals(endDate, project.endDate) && Objects.equals(version, project.version) && Objects.equals(projectEmployees, project.projectEmployees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, projectNumber, name, customer, status, startDate, endDate, version, projectEmployees);
    }
}