package com.diegotobalina.framework.customizable.entities.example.application.usecase;

import com.diegotobalina.framework.customizable.entities.example.domain.Example;
import com.diegotobalina.framework.provided.anotation.UseCase;
import com.diegotobalina.framework.provided.interfaces.usecase.DeleteUseCase;
import com.diegotobalina.framework.provided.interfaces.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class DeleteExampleUseCase implements DeleteUseCase<Example> {
  @Override
  public void preLoad(long id, CrudService<Example> service, JpaRepository<Example, Long> repository) {/* empty */}

  @Override
  public void postLoad(
          Example example, CrudService<Example> service, JpaRepository<Example, Long> repository) {/* empty */}

  @Override
  public void postDelete(
          Example example, CrudService<Example> service, JpaRepository<Example, Long> repository) {/* empty */}
}
