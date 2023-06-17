package vn.elca.training.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author gtn
 * @author thomas.dang
 */
@Data
public class ProjectDto {
    private Long id;
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate finishingDate;
    private String customer;
}
