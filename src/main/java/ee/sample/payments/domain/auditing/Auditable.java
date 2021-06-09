package ee.sample.payments.domain.auditing;

import ee.sample.payments.domain.BaseEntity;
import lombok.Getter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable extends BaseEntity {

  @LastModifiedBy
  @CreatedBy
  @Column(name = "UPDATED_BY")
  private String lastModifiedBy;

  @LastModifiedDate
  @CreatedDate
  @Column(name = "UPDATED_AT")
  private LocalDateTime lastModifiedDate;

  @CreatedBy
  @Column(name = "CREATED_BY")
  private String createdBy;

  @CreatedDate
  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  private LocalDateTime createdDateTime;
}
