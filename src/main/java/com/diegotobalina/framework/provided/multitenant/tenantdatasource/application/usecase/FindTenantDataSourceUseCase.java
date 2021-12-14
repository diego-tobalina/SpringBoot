package com.diegotobalina.framework.provided.multitenant.tenantdatasource.application.usecase;

import com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain.TenantDataSource;
import com.diegotobalina.framework.provided.anotation.UseCase;
import com.diegotobalina.framework.provided.interfaces.usecase.FindUseCase;
import com.diegotobalina.framework.provided.interfaces.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class FindTenantDataSourceUseCase implements FindUseCase<TenantDataSource> {
  @Override
  public void preLoad(long id, CrudService<TenantDataSource> service, JpaRepository<TenantDataSource, Long> repository) {/* empty */}

  @Override
  public void postLoad(
          TenantDataSource tenantdatasource, CrudService<TenantDataSource> service, JpaRepository<TenantDataSource, Long> repository) {/* empty */}
}
