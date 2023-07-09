package vn.elca.training.util;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.ProjectDTO;
import vn.elca.training.model.entity.Project;

@Component
public class ApplicationMapper {
    public ApplicationMapper() {
    }

    public ProjectDTO projectToProjectDto(Project entity) {
        ProjectDTO dto = new ProjectDTO();
        dto.setGroupId(entity.getGroupId().getId());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setCustomer(entity.getCustomer());
        dto.setStatus(entity.getStatus().getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setVersion(entity.getVersion());
        return dto;
    }
}
