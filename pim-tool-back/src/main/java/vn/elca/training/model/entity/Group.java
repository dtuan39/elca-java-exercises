package vn.elca.training.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author thomas.dang
 */
@Entity
@Getter
@Setter
@Table(name = "\"GROUP\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_LEADER_ID", nullable = false)
    private Employee groupLeader;

    @Column(name = "VERSION", nullable = false)
    private Integer version;
}
