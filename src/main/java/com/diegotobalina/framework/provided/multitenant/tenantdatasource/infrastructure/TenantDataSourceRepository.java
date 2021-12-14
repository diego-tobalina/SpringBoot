package com.diegotobalina.framework.provided.multitenant.tenantdatasource.infrastructure;

import com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain.TenantDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** @author diegotobalina created on 9/12/21 */
public interface TenantDataSourceRepository extends JpaRepository<TenantDataSource, Long> {
  Optional<TenantDataSource> findByHeaderTokenAndActive(String name, boolean b);
}
