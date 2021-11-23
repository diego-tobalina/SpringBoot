package com.xbidi.spring.config.multitenant.infrastrcture;

import com.xbidi.spring.content.shared.Constants;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
    extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  private final transient DataSource defaultDS;
  private final transient ApplicationContext context;
  private final transient Map<String, DataSource> map = new HashMap<>();
  private boolean init = false;

  public DataSourceBasedMultiTenantConnectionProviderImpl(
      DataSource defaultDS, ApplicationContext context) {
    this.defaultDS = defaultDS;
    this.context = context;
  }

  @PostConstruct
  public void load() {
    map.put(Constants.MULTITENANT_DEFAULT_DB, defaultDS);
  }

  @Override
  protected DataSource selectAnyDataSource() {
    return map.get(Constants.MULTITENANT_DEFAULT_DB);
  }

  @Override
  protected DataSource selectDataSource(String tenantIdentifier) {
    if (!init) {
      init = true;
      TenantRepository tenantRepository = context.getBean(TenantRepository.class);
      map.putAll(tenantRepository.getAll());
    }
    return map.get(tenantIdentifier) != null
        ? map.get(tenantIdentifier)
        : map.get(Constants.MULTITENANT_DEFAULT_DB);
  }
}
