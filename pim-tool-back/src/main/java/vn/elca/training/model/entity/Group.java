package vn.elca.training.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`group`")
public class Group1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "group")
    private List<Project> project = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "groupLeaderId", referencedColumnName = "id")
    private Employee employee;
//    @Column
//    private int groupLeaderID;

    @Column
    private int version;

    public Group1() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getGroupLeaderID() {
//        return groupLeaderID;
//    }
//
//    public void setGroupLeaderID(int groupLeaderID) {
//        this.groupLeaderID = groupLeaderID;
//    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
