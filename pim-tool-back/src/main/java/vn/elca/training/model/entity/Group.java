package vn.elca.training.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "\"group\"")
public class Group {
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

    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
