package info.jakepark.springboot2.business.common.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractPersistableEntity<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    ID id;

    @Version
    Long version;

}
