package vn.elca.training.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.elca.training.model.entity.Employee;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProjectDTO {

    @Positive(message = "Group ID must be positive")
    @NotNull(message = "Group ID is required and must not be blank")
    private Long groupId;

    @Min(value = 1, message = "Project Number must be greater than or equals to 1")
    @Max(value = 9999, message = "Project Number must be lower than or equals to 9999")
    @NotNull(message = "Project Number is required and must not be blank")
    private Integer projectNumber;

    @Size(min = 1, max = 50, message = "Project name must be from 1 to 50 characters")
    @NotBlank(message = "Project Name is required and must not be blank")
    private String name;

    private List<Employee> employees;

    @NotBlank(message = "Customer name is required")
    private String customer;

    @NotBlank(message = "Project Name is required and must not be blank")
    @Pattern(regexp = "^(NEW|PLA|INP|FIN)$", message = "Invalid status. Allowed values are: NEW, PLA, INF, FIN")
    @Size(min = 3, max = 3, message = "Status length must be exactly 3 characters")
    private String status;

    @NotNull(message = "Start Date is required!")
    private LocalDate startDate;

    private LocalDate endDate;

    private Long version;
}
