package com.diegotobalina.framework.core.multitenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        return this.selectAnyDataSource();
    }

    /**
     * Genera un nuevo dataSource a partir de los parámetros de conexión
     *
     * @param driverClassName org.postgresql.Driver
     * @param username        postgres
     * @param password        password
     * @param url             jdbc:postgresql://localhost:5432/postgres?currentSchema=public&ApplicationName=SpringBootTenant1&sslmode=disable
     * @return El dataSource generado
     */
    private DataSource buildDataSource(String driverClassName, String username, String password, String url) {
        return DataSourceBuilder
                .create().driverClassName(driverClassName)
                .username(username)
                .password(password)
                .url(url).build();
    }
}
