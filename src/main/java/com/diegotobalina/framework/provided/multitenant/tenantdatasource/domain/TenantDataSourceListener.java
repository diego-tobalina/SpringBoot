package com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/** @author diegotobalina created on 9/12/21 */
@Component
public class TenantDataSourceListener {

  @PrePersist
  public void prePersist(TenantDataSource tenantDataSource) {
    /* empty */
  }

  @PreUpdate
  public void preUpdate(TenantDataSource tenantDataSource) {
    /* empty */
  }

  @PreRemove
  public void preRemove(TenantDataSource tenantDataSource) {
    /* empty */
  }

  @PostLoad
  public void postLoad(TenantDataSource tenantDataSource) {
    /* empty */
  }

  @PostPersist
  public void postPersist(TenantDataSource tenantDataSource) {
    /* empty */
  }

  @PostUpdate
  public void postUpdate(TenantDataSource tenantDataSource) {
    /* empty */
  }

  @PostRemove
  public void postRemove(TenantDataSource tenantDataSource) {
    /* empty */
  }
}
