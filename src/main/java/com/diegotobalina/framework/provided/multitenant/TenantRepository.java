package com.diegotobalina.framework.provided.multitenant;

import com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain.TenantDataSource;
import com.diegotobalina.framework.provided.multitenant.tenantdatasource.infrastructure.repository.TenantDataSourceRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantRepository implements Serializable {

  private final transient HashMap<String, DataSource> dataSources = new HashMap<>();

  private final transient TenantDataSourceRepository configRepo;

  public TenantRepository(TenantDataSourceRepository configRepo) {
    this.configRepo = configRepo;
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
    List<TenantDataSource> configList = configRepo.findAll();
    Map<String, DataSource> result = new HashMap<>();
    for (TenantDataSource config : configList) {
      DataSource dataSource = getDataSource(config.getHeaderToken());
      result.put(config.getHeaderToken(), dataSource);
    }
    return result;
  }

  private DataSource createDataSource(String name) {
    TenantDataSource config = configRepo.findByHeaderTokenAndActive(name, true);
    if (config != null) {
      DataSourceBuilder<?> factory =
          DataSourceBuilder.create()
              .driverClassName(config.getDriverClassName())
              .username(config.getUsername())
              .password(config.getPassword())
              .url(config.getUrl());
      return factory.build();
    }
    return null;
  }
}
