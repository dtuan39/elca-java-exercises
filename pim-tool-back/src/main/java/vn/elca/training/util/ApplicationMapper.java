package vn.elca.training.util;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;

@Component
public class ApplicationMapper {
    public ApplicationMapper() {
    }

    public ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setCustomer(entity.getCustomer());
        dto.setGroupId(entity.getGroup().getId());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
