package com.diegotobalina.framework.customizable.entities.example.application.usecase;

import com.diegotobalina.framework.core.anotation.UseCase;
import com.diegotobalina.framework.core.crud.services.IBaseService;
import com.diegotobalina.framework.core.crud.usecases.IFindUseCase;
import com.diegotobalina.framework.customizable.entities.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

@UseCase
public class IFindExampleUseCase implements IFindUseCase<Example> {
  @Override
  public void preLoad(
      long id, IBaseService<Example> service, JpaRepository<Example, Long> repository) {
    /* implement */
  }

  @Override
  public void postLoad(
      Example example, IBaseService<Example> service, JpaRepository<Example, Long> repository) {
    /* implement */
  }
}
