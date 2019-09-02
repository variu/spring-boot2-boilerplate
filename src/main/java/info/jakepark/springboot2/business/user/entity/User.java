package info.jakepark.springboot2.business.user.entity;

import info.jakepark.springboot2.business.common.entity.AbstractAuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends AbstractAuditEntity<User, Long> {

    @Column(nullable = false)
    private String name;

    @Column
    private String password;

    @Column
    private Integer status;
}