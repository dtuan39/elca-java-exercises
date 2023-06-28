package pilotproject.Project_Management_ELCA.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Group\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int version;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_leader_id")
    private Employee employee;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private Set<Project> projectList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return version == group.version && Objects.equals(id, group.id) && Objects.equals(employee, group.employee) && Objects.equals(projectList, group.projectList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, employee, projectList);
    }
}
