package vn.elca.training.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GroupId")
    private Group1 group;

    @OneToMany(mappedBy = "project")
    private List<ProjectEmployee> projectEmployees = new ArrayList<>();

//    @Column
//    private int groupID;

    @Column
    private String status;

    @Column
    private LocalDate startDate;

    @Column(nullable = false)
    private String name;

    @Column
    private String PROJECT_NUMBER;

    @Column
    private LocalDate endDate;

    @Column
    private String customer;

    @Column
    private int version;

    public Project() {
    }

    public Project(Long id, Group1 group, String status, LocalDate startDate, String name, String PROJECT_NUMBER, LocalDate endDate, String customer, int version) {
        this.id = id;
        this.group = group;
        this.status = status;
        this.startDate = startDate;
        this.name = name;
        this.PROJECT_NUMBER = PROJECT_NUMBER;
        this.endDate = endDate;
        this.customer = customer;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public int getGroupID() {
//        return groupID;
//    }
//
//    public void setGroupID(int groupID) {
//        this.groupID = groupID;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getPROJECT_NUMBER() {
        return PROJECT_NUMBER;
    }

    public void setPROJECT_NUMBER(String PROJECT_NUMBER) {
        this.PROJECT_NUMBER = PROJECT_NUMBER;
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
        return "Project2{" +
                "id=" + id +
                ", group=" + group +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", name='" + name + '\'' +
                ", PROJECT_NUMBER='" + PROJECT_NUMBER + '\'' +
                ", endDate=" + endDate +
                ", customer='" + customer + '\'' +
                ", version=" + version +
                '}';
    }

    //    @Override
//    public String toString() {
//        return "Project2{" +
//                "id=" + id +
//                ", groupID=" + groupID +
//                ", status='" + status + '\'' +
//                ", startDate=" + startDate +
//                ", name='" + name + '\'' +
//                ", PROJECT_NUMBER='" + PROJECT_NUMBER + '\'' +
//                ", endDate=" + endDate +
//                ", customer='" + customer + '\'' +
//                ", version=" + version +
//                '}';
//    }
}
