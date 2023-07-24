package vn.elca.training.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link vn.elca.training.model.entity.Group}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String groupLeaderVisa;
    private int version;
}