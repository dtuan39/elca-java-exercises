package vn.elca.training.model.entity;

import javax.persistence.*;

@Entity
public class ProjectEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "projectId")
    Project project = new Project();

    @ManyToOne
    @JoinColumn(name = "employeeId")
    Employee employee = new Employee();
}
