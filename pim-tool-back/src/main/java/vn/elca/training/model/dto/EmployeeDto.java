package vn.elca.training.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link vn.elca.training.model.entity.Employee}
 */
@Data
@RequiredArgsConstructor
public class EmployeeDto implements Serializable {
    private Long id;
    private String visa;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Integer version;
}