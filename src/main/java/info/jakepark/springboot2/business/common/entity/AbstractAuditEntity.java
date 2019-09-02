package info.jakepark.springboot2.business.common.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity<U, ID> extends AbstractPersistableEntity<ID> {

    @CreatedDate
    protected LocalDateTime createdAt;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by")
    protected U createdBy;

    @LastModifiedDate
    protected LocalDateTime modifiedAt;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "modified_by")
    protected U modifiedBy;

}
