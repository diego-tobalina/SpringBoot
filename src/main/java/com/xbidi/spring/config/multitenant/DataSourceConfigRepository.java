package com.xbidi.spring.config.multitenant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfig, Long> {
  DataSourceConfig findByNameAndActive(String name, boolean active);
}
