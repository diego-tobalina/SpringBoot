package com.diegotobalina.framework.customizable.entities.example.application.usecase;

import com.diegotobalina.framework.customizable.entities.example.domain.Example;
import com.diegotobalina.framework.provided.anotation.UseCase;
import com.diegotobalina.framework.provided.interfaces.service.CrudService;
import com.diegotobalina.framework.provided.interfaces.usecase.CreateUseCase;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class CreateExampleUseCase implements CreateUseCase<Example> {
  @Override
  public void preSave(
      Example example, CrudService<Example> service, JpaRepository<Example, Long> repository) {
    /* empty */
  }

  @Override
  public void postSave(
      Example example, CrudService<Example> service, JpaRepository<Example, Long> repository) {
    /* empty */
  }
}
