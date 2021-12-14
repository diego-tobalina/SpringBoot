package com.diegotobalina.framework.provided.multitenant.tenantdatasource.application.usecase;

import com.diegotobalina.framework.provided.anotation.UseCase;
import com.diegotobalina.framework.provided.interfaces.service.CrudService;
import com.diegotobalina.framework.provided.interfaces.usecase.CreateUseCase;
import com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain.TenantDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class CreateTenantDataSourceUseCase implements CreateUseCase<TenantDataSource> {
  @Override
  public void preSave(
      TenantDataSource tenantdatasource,
      CrudService<TenantDataSource> service,
      JpaRepository<TenantDataSource, Long> repository) {
    /* empty */
  }

  @Override
  public void postSave(
      TenantDataSource tenantdatasource,
      CrudService<TenantDataSource> service,
      JpaRepository<TenantDataSource, Long> repository) {
    /* empty */
  }
}
