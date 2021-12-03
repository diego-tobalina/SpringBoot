/* Autogenerated file. Do not edit manually. */

package com.diegotobalina.framework.provided.interfaces.usecase;

import com.diegotobalina.framework.provided.interfaces.service.CrudService;
import com.diegotobalina.framework.provided.interfaces.entity.CustomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateUseCase<T extends CustomEntity> {
  default T update(long id, Object o, CrudService<T> service, JpaRepository<T, Long> repository) {
    preLoad(id, o, service, repository);
    T t = service.findById(id, repository);
    preUpdate(t, o, service, repository);
    t.update(o, new String[] {"id"});
    preSave(t, o, service, repository);
    T save = service.save(t, repository);
    postSave(save, o, service, repository);
    return save;
  }

  void preLoad(long id, Object o, CrudService<T> service, JpaRepository<T, Long> repository);

  void preUpdate(T t, Object o, CrudService<T> service, JpaRepository<T, Long> repository);

  void preSave(T t, Object o, CrudService<T> service, JpaRepository<T, Long> repository);

  void postSave(T t, Object o, CrudService<T> service, JpaRepository<T, Long> repository);
}