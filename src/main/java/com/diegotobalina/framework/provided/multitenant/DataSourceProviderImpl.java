package com.diegotobalina.framework.provided.multitenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceProviderImpl
    extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  private DataSource defaultDataSource;

  public DataSourceProviderImpl(DataSource defaultDataSource) {
    this.defaultDataSource = defaultDataSource;
  }

  @Override
  protected DataSource selectAnyDataSource() {
    return this.defaultDataSource;
  }

  @Override
  protected DataSource selectDataSource(String tenantIdentifier) {
    switch (tenantIdentifier) {
      default -> this.selectAnyDataSource();
    }
    throw new IllegalArgumentException("Unknown tenant identifier: " + tenantIdentifier);
  }
}
