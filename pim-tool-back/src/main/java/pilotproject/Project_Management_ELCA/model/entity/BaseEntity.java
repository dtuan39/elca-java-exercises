package pilotproject.Project_Management_ELCA.model.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

        // If you need to use distributed caching, distributed systems, or session replication,
        // where serializability might be beneficial or even mandatory.
        // public abstract class AbstractEntity implements Serializable {

         @Id
         @GeneratedValue(strategy = GenerationType.IDENTITY)
         @Column(name = "ID", nullable = false)
         private Long id;

         @Version
         @Column(name = "VERSION", nullable = false)
         private int version;
}
