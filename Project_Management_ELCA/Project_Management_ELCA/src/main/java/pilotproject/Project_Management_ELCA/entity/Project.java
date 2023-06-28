package pilotproject.Project_Management_ELCA.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//import javax.validation.constraints.Future;
//import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author vlp
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(unique = true, nullable = false)
    private int project_number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String customer;

    @Column(nullable = false)
//    @Pattern(regexp = "^(NEW|PLA|INP|FIN)$", message = "Invalid value. Please choose from: NEW, PLA, INP, FIN")
    private String status;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column
//    @Future
    private LocalDate end_date;

    @Column(nullable = false)
    private int version;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PROJECT_EMPLOYEE",
            joinColumns = {@JoinColumn(name = "PROJECT_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "id")})
    private Set<Employee> employeeList;


}