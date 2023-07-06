package vn.elca.training.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link vn.elca.training.model.entity.Project}
 */
@Data
@RequiredArgsConstructor
public class ProjectDto implements Serializable {
    private Long id;
    @JsonProperty("number")
    private Integer projectNumber;
    private String name;
    private String customer;
    private Long groupId;
    private String members;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int version;
}