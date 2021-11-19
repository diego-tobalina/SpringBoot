package com.xbidi.spring.config.multitenant.domain;

import com.xbidi.spring.config.multitenant.infrastrcture.DataSourceConfigRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantDataSource implements Serializable {

  private final transient HashMap<String, DataSource> dataSources = new HashMap<>();

  private final transient DataSourceConfigRepository configRepo;

  public TenantDataSource(DataSourceConfigRepository configRepo) {
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
    List<TenantDataSourceConfig> configList = configRepo.findAll();
    Map<String, DataSource> result = new HashMap<>();
    for (TenantDataSourceConfig config : configList) {
      DataSource dataSource = getDataSource(config.getName());
      result.put(config.getName(), dataSource);
    }
    return result;
  }

  private DataSource createDataSource(String name) {
    TenantDataSourceConfig config = configRepo.findByNameAndActive(name, true);
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