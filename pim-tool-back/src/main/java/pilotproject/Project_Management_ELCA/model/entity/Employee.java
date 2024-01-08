package pilotproject.Project_Management_ELCA.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
public class Employee extends BaseEntity {
    @Column(nullable = false)
    private String visa;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date birthDate;
}
