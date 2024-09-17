package pilotproject.Project_Management_ELCA.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author vlp
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity{
    @ManyToOne()
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(unique = true, nullable = false)
    private int projectNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String customer;

    public enum Status {
        NEW,
        PLA,
        INP,
        FIN
    }
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "PROJECT_EMPLOYEE",
            joinColumns = {@JoinColumn(name = "PROJECT_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "id")})
    private Set<Employee> employeeList;
}