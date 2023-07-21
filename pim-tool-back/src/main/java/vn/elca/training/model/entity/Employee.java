package vn.elca.training.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author thomas.dang
 */
@Entity
@Getter
@Setter
@Table(name = "EMPLOYEE")
public class Employee extends AbstractEntity {

    @Column(name = "VISA", nullable = false, length = 3, unique = true)
    private String visa;

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

}

