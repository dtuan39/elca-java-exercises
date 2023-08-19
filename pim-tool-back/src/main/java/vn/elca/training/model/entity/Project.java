package vn.elca.training.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author thomas.dang
 */
@Entity
@Getter
@Setter
@Table(name = "PROJECT")
public class Project extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private Group group;

    @Column(name = "PROJECT_NUMBER", nullable = false, unique = true)
    private Integer projectNumber;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "CUSTOMER", nullable = false, length = 50)
    private String customer;

    @Column(name = "STATUS", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Getter
    @AllArgsConstructor
    public enum Status {
        NEW("New"),
        PLA("Planned"),
        INP("In progress"),
        FIN("Finished");
        private final String displayName;
    }

    @ManyToMany
    @JoinTable(name = "PROJECT_EMPLOYEE",
            joinColumns = @JoinColumn(name = "PROJECT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private Set<Employee> employees = new HashSet<>();
}