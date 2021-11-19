package com.xbidi.spring.config.multitenant.infrastrcture;

import com.xbidi.spring.config.multitenant.domain.TenantDataSourceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceConfigRepository extends JpaRepository<TenantDataSourceConfig, Long> {
  TenantDataSourceConfig findByNameAndActive(String name, boolean active);
}
