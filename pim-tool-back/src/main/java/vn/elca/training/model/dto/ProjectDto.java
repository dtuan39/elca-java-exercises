package vn.elca.training.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * @author gtn
 */
public class ProjectDto {
    private Long id;
    private String name;
    @JsonFormat(pattern = "d/M/yyyy")
    private LocalDate finishingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }
}
