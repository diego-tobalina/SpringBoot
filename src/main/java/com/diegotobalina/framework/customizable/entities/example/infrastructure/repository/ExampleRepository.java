/* Autogenerated file. Do not edit manually. */

package com.diegotobalina.framework.customizable.entities.example.infrastructure.repository;

import com.diegotobalina.framework.customizable.entities.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository
    extends JpaRepository<Example, Long>, JpaSpecificationExecutor<Example> {}