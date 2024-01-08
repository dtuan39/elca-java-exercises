package pilotproject.Project_Management_ELCA.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDto {
    private Long id;
    private String visa;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private int version;
}
