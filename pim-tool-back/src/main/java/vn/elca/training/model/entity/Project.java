package vn.elca.training.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GroupId")
    private Group group;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "ProjectEmployee",
            joinColumns = @JoinColumn(name = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "projectId")
    )
    private List<Project> projects;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private LocalDate startDate;

    @Column(nullable = false)
    private String name;

    @Column
    private int projectNumber;

    @Column
    private LocalDate endDate;

    @Column
    private String customer;

    @Column
    private int version;

    public Project() {
    }

    public Project(Long id, Group group, List<Project> projects, Status status, LocalDate startDate, String name, int projectNumber, LocalDate endDate, String customer, int version) {
        this.id = id;
        this.group = group;
        this.projects = projects;
        this.status = status;
        this.startDate = startDate;
        this.name = name;
        this.projectNumber = projectNumber;
        this.endDate = endDate;
        this.customer = customer;
        this.version = version;
    }

    public Project(Group group, List<Project> projects, Status status, LocalDate startDate, String name, int projectNumber, LocalDate endDate, String customer, int version) {
        this.group = group;
        this.projects = projects;
        this.status = status;
        this.startDate = startDate;
        this.name = name;
        this.projectNumber = projectNumber;
        this.endDate = endDate;
        this.customer = customer;
        this.version = version;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", group=" + group +
                ", projects=" + projects +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", name='" + name + '\'' +
                ", projectNumber=" + projectNumber +
                ", endDate=" + endDate +
                ", customer='" + customer + '\'' +
                ", version=" + version +
                '}';
    }

}
