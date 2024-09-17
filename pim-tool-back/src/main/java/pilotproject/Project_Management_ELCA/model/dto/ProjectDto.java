package pilotproject.Project_Management_ELCA.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDto {
    private Long id;
    private int number;
    private String name;
    private String customer;
    private Long groupId;
    private String groupLeaderVisa;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    public enum StatusDto {
        NEW,
        PLA,
        INP,
        FIN
    }
    private StatusDto status;
    private int version;
}
