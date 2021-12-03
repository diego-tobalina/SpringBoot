package com.diegotobalina.framework.customizable.entities.example.application.usecase;

import com.diegotobalina.framework.customizable.entities.example.domain.Example;
import com.diegotobalina.framework.provided.anotation.UseCase;
import com.diegotobalina.framework.provided.interfaces.usecase.UpdateUseCase;
import com.diegotobalina.framework.provided.interfaces.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class UpdateExampleUseCase implements UpdateUseCase<Example> {
  @Override
  public void preLoad(
          long id, Object o, CrudService<Example> service, JpaRepository<Example, Long> repository) {/* empty */}

  @Override
  public void preUpdate(
          Example example, Object o, CrudService<Example> service, JpaRepository<Example, Long> repository) {/* empty */}

  @Override
  public void preSave(
          Example example, Object o, CrudService<Example> service, JpaRepository<Example, Long> repository) {/* empty */}

  @Override
  public void postSave(
          Example example, Object o, CrudService<Example> service, JpaRepository<Example, Long> repository) {/* empty */}
}
