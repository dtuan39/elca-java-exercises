package pilotproject.Project_Management_ELCA.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class SearchResultResponse {
    private int totalCount;
    private List<ProjectDto> results;
}
