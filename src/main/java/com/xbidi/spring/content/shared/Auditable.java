package com.xbidi.spring.content.shared;

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
  @Column(name = "CREATED_BY")
  private U createdBy;

  @CreatedDate
  @Column(name = "CREATED_ON")
  private Instant createdOn;

  @LastModifiedBy
  @Column(name = "MODIFIED_BY")
  private U lastModifiedBy;

  @LastModifiedDate
  @Column(name = "MODIFIED_ON")
  private Instant lastModifiedOn;
}