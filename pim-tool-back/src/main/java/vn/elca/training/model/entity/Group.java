package vn.elca.training.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leader_id")
    private User leader;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_PROJECT_TABLE",
            joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")})
    private Set<Project> projectList;


    public Group() {
    }

    public Group(Integer id, User leader, Set<Project> projectList) {
        this.id = id;
        this.leader = leader;
        this.projectList = projectList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public Set<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(Set<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", leader=" + leader +
                ", projectList=" + projectList +
                '}';
    }
}
