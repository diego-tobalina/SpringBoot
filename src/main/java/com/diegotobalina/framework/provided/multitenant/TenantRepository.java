package com.diegotobalina.framework.provided.multitenant;

import com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain.TenantDataSource;
import com.diegotobalina.framework.provided.multitenant.tenantdatasource.infrastructure.TenantDataSourceRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TenantRepository implements Serializable {

  private final transient HashMap<String, DataSource> dataSources = new HashMap<>();

  private final transient TenantDataSourceRepository tenantDataSourceRepository;

  public TenantRepository(TenantDataSourceRepository tenantDataSourceRepository) {
    this.tenantDataSourceRepository = tenantDataSourceRepository;
  }

  public DataSource getDataSource(String name) {
    if (dataSources.get(name) != null) {
      return dataSources.get(name);
    }
    DataSource dataSource = createDataSource(name);
    if (dataSource != null) {
      dataSources.put(name, dataSource);
    }
    return dataSource;
  }

  @PostConstruct
  public Map<String, DataSource> getAll() {
    List<TenantDataSource> configList = tenantDataSourceRepository.findAll();
    Map<String, DataSource> result = new HashMap<>();
    for (TenantDataSource config : configList) {
      DataSource dataSource = getDataSource(config.getHeaderToken());
      result.put(config.getHeaderToken(), dataSource);
    }
    return result;
  }

  private DataSource createDataSource(String name) {
    Optional<TenantDataSource> optional =
        tenantDataSourceRepository.findByHeaderTokenAndActive(name, true);
    if (optional.isPresent()) {
      TenantDataSource tenantDataSource = optional.get();
      DataSourceBuilder<?> factory =
          DataSourceBuilder.create()
              .driverClassName(tenantDataSource.getDriverClassName())
              .username(tenantDataSource.getUsername())
              .password(tenantDataSource.getPassword())
              .url(tenantDataSource.getUrl());
      return factory.build();
    }
    return null;
  }
}
