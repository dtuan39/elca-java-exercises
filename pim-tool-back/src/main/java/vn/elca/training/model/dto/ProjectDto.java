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
    private static final long serialVersionUID = 1L;
    @JsonProperty("number")
    private Integer projectNumber;
    private Long id;
    private String name;
    private String customer;
    private Long groupId;
    private String members;
    private StatusDto status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int version;
    public enum StatusDto {
        NEW, PLA, INP, FIN
    }
}