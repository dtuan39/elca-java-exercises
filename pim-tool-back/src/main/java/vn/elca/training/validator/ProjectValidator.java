package vn.elca.training.validator;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.exception.StartDateAfterEndDateException;

@Component
public class ProjectValidator {

    public void validate(ProjectDto projectDto) throws StartDateAfterEndDateException {
        // Check mandatory fields except id auto-generated, version auto-generated, members
        if (projectDto.getName() == null || projectDto.getName().isEmpty()) {
            // IllegalArgumentException is runtime exception
            throw new IllegalArgumentException("Project name is mandatory");
        }
        if (projectDto.getCustomer() == null || projectDto.getCustomer().isEmpty()) {
            throw new IllegalArgumentException("Customer is mandatory");
        }
        if (projectDto.getGroupId() == null) {
            throw new IllegalArgumentException("Group is mandatory");
        }
        if (projectDto.getMembers() == null || projectDto.getMembers().isEmpty()) {
            throw new IllegalArgumentException("Members is mandatory");
        }
        if (projectDto.getStatus() == null) {
            throw new IllegalArgumentException("Status is mandatory");
        }
        if (projectDto.getProjectNumber() == null) {
            throw new IllegalArgumentException("Project number is mandatory");
        }
        if (projectDto.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is mandatory");
        }
        // Check if start date is after end date
        if (projectDto.getEndDate() != null && projectDto.getStartDate().isAfter(projectDto.getEndDate())) {
            throw new StartDateAfterEndDateException(projectDto.getEndDate(),
                    projectDto.getStartDate());
        }
    }
}
