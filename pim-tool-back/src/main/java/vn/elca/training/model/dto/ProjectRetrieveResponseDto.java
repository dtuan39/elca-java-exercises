package vn.elca.training.model.dto;

import lombok.Data;

import java.util.List;

/**
 * This class is used to return a list of projects and total count of projects
 * to the client.
 */

@Data
public class ProjectRetrieveResponseDto {
    private List<ProjectDto> projects;
    private long totalCount;
    public ProjectRetrieveResponseDto(List<ProjectDto> projects, long totalCount) {
        this.projects = projects;
        this.totalCount = totalCount;
    }
}
