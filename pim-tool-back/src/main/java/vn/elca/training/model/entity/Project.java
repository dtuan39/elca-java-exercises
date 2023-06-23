package vn.elca.training.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author thomas.dang
 */
@Entity
@Getter
@Setter
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private Group group;

    @Column(name = "PROJECT_NUMBER", nullable = false)
    private Integer projectNumber;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "CUSTOMER", nullable = false, length = 50)
    private String customer;

    @Column(name = "STATUS", nullable = false, length = 3)
    private String status;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "VERSION", nullable = false)
    private Integer version;
}