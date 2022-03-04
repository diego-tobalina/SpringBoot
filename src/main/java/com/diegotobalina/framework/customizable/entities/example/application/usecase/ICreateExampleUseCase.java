package com.diegotobalina.framework.customizable.entities.example.application.usecase;

import com.diegotobalina.framework.core.anotation.UseCase;
import com.diegotobalina.framework.core.crud.services.IBaseService;
import com.diegotobalina.framework.core.crud.usecases.ICreateUseCase;
import com.diegotobalina.framework.customizable.entities.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class ICreateExampleUseCase implements ICreateUseCase<Example> {
  @Override
  public void preSave(
      Example example, IBaseService<Example> service, JpaRepository<Example, Long> repository) {
    /* implement */
  }

  @Override
  public void postSave(
      Example example, IBaseService<Example> service, JpaRepository<Example, Long> repository) {
    /* implement */
  }
}
