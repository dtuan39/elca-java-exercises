package vn.elca.training.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "PROJECT_MEMBER_TABLE")
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private User member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    // Additional columns specific to the join table
    @Column(name = "status")
    private String status;

    public ProjectMember() {
    }

    public ProjectMember(Long id, User member, Project project, String status) {
        this.id = id;
        this.member = member;
        this.project = project;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
