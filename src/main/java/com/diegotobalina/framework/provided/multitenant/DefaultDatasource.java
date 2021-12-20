package com.diegotobalina.framework.provided.multitenant;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DefaultDatasource {

  @Bean
  public DataSource defaultDataSource() {
    String driverClassName = "org.postgresql.Driver";
    String userName = "postgres";
    String password = "password";
    String host = "localhost";
    String port = "5432";
    String database = "postgres";
    String schema = "public";
    String applicationName = "spring";
    String baseUrlString = "jdbc:postgresql://%s:%s/%s?currentSchema=%s&ApplicationName=%s";
    String url = String.format(baseUrlString, host, port, database, schema, applicationName);
    return DataSourceBuilder.create()
        .driverClassName(driverClassName)
        .username(userName)
        .password(password)
        .url(url)
        .build();
  }
}
