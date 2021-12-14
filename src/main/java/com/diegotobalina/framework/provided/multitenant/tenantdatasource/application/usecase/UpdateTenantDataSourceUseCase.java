package com.diegotobalina.framework.provided.multitenant.tenantdatasource.application.usecase;

import com.diegotobalina.framework.provided.anotation.UseCase;
import com.diegotobalina.framework.provided.interfaces.service.CrudService;
import com.diegotobalina.framework.provided.interfaces.usecase.UpdateUseCase;
import com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain.TenantDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class UpdateTenantDataSourceUseCase implements UpdateUseCase<TenantDataSource> {
  @Override
  public void preLoad(
      long id,
      Object o,
      CrudService<TenantDataSource> service,
      JpaRepository<TenantDataSource, Long> repository) {
    /* empty */
  }

  @Override
  public void preUpdate(
      TenantDataSource tenantdatasource,
      Object o,
      CrudService<TenantDataSource> service,
      JpaRepository<TenantDataSource, Long> repository) {
    /* empty */
  }

  @Override
  public void preSave(
      TenantDataSource tenantdatasource,
      Object o,
      CrudService<TenantDataSource> service,
      JpaRepository<TenantDataSource, Long> repository) {
    /* empty */
  }

  @Override
  public void postSave(
      TenantDataSource tenantdatasource,
      Object o,
      CrudService<TenantDataSource> service,
      JpaRepository<TenantDataSource, Long> repository) {
    /* empty */
  }
}
