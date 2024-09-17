package pilotproject.Project_Management_ELCA.model.dto;

import lombok.Data;

@Data
public class ProjectMembersDto {
    private ProjectDto projectDto;
    private int[] listEmpId;
}
