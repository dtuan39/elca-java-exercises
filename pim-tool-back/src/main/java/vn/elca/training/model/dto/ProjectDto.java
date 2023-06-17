package vn.elca.training.model.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * @author gtn
 * @author thomas.dang
 */
@Data
public class ProjectDto {
    private Long id;
    private String name;
    private LocalDate finishingDate;
    private String customer;
}
