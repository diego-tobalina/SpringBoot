package com.diegotobalina.framework.core.auditable;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@Setter
@Audited
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {

  @CreatedBy
  @Column(name = "created_by")
  private U createdBy;

  @CreatedDate
  @Column(name = "created_on")
  private Instant createdOn;

  @LastModifiedBy
  @Column(name = "modified_by")
  private U lastModifiedBy;

  @LastModifiedDate
  @Column(name = "modified_on")
  private Instant lastModifiedOn;
}
