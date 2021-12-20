package com.diegotobalina.framework.provided.multitenant;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DefaultDatasource {

  @Bean
  public DataSource defaultDataSource() {
    return DataSourceBuilder.create()
        .driverClassName("org.postgresql.Driver")
        .username("postgres")
        .password("password")
        .url("jdbc:postgresql://localhost:5432/postgres?ApplicationName=MultiTenant")
        .build();
  }
}
