package com.diegotobalina.framework.customizable.entities.example.application.usecase;

import com.diegotobalina.framework.core.anotation.UseCase;
import com.diegotobalina.framework.core.crud.services.IBaseService;
import com.diegotobalina.framework.core.crud.usecases.IUpdateUseCase;
import com.diegotobalina.framework.customizable.entities.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class IUpdateExampleUseCase implements IUpdateUseCase<Example> {
  @Override
  public void preLoad(
      long id, Object o, IBaseService<Example> service, JpaRepository<Example, Long> repository) {
    /* implement */
  }

  @Override
  public void preUpdate(
      Example example,
      Object o,
      IBaseService<Example> service,
      JpaRepository<Example, Long> repository) {
    /* implement */
  }

  @Override
  public void preSave(
      Example example,
      Object o,
      IBaseService<Example> service,
      JpaRepository<Example, Long> repository) {
    /* implement */
  }

  @Override
  public void postSave(
      Example example,
      Object o,
      IBaseService<Example> service,
      JpaRepository<Example, Long> repository) {
    /* implement */
  }
}
