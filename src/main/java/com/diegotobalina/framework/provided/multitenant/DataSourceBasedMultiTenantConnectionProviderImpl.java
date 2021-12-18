package com.diegotobalina.framework.provided.multitenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
    extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  @Override
  protected DataSource selectAnyDataSource() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  protected DataSource selectDataSource(String tenantIdentifier) {
    DataSourceBuilder<?> factory =
        DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .username("postgres")
            .password("password")
            .url("jdbc:postgresql://localhost:5432/postgres?ApplicationName=MultiTenant");
    return factory.build();
  }
}
