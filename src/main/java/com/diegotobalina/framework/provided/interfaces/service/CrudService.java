package com.diegotobalina.framework.provided.interfaces.service;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CrudService<T> {

  /**
   * Devuelve la entidad filtrando por el id
   *
   * @param id Id sobre el que se filtra
   * @return La entidad encontrada
   */
  default T findById(long id, JpaRepository<T, Long> repository) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> {
              var className = (T) getClass().getSimpleName();
              var errorMessage = String.format("%s not found with id: %d", className, id);
              return new EntityNotFoundException(errorMessage);
            });
  }

  /**
   * Devuelve la entidad filtrando por el id, en el caso de no encontrarla devuelve null
   *
   * @param id Id sobre el que se filtra
   * @return La entidad encontrada o null
   * @throws IllegalArgumentException Si el id es null o está vacío
   */
  default T findByIdOrNull(long id, JpaRepository<T, Long> repository) {
    return repository.findById(id).orElse(null);
  }

  /**
   * Devuelve todas las entidades que encuentre con los ids
   *
   * @param ids Ids sobre los que filtrar
   * @return La lista con las entidades encontradas
   * @throws IllegalArgumentException Si el ids es null
   */
  default List<T> findAllByIds(List<Long> ids, JpaRepository<T, Long> repository) {
    if (ids == null) {
      var errorMessage = String.format("%s cant be null", "ids");
      throw new IllegalArgumentException(errorMessage);
    }
    return repository.findAllById(ids);
  }

  /**
   * Guarda la entidad, si no existía la crea y si existía la actualiza
   *
   * @param t Entidad que quieres guardar
   * @return Devuelve la entidad creada o actualizada
   * @throws IllegalArgumentException Si la entidad es null
   */
  default T save(T t, JpaRepository<T, Long> repository) {
    if (t == null) {
      throw new IllegalArgumentException("entity to save cant be null");
    }
    return repository.save(t);
  }

  /**
   * Guarda todas las entidades, las que no existen las crea y las que existen las actualiza
   *
   * @param t Lista con las entidades que quieres crear o actualizar
   * @return Devuelve la lista de las entidades creadas o actualizadas
   * @throws IllegalArgumentException Si la lista de entidades es null
   */
  default List<T> saveAll(List<T> t, JpaRepository<T, Long> repository) {
    if (t == null) {
      throw new IllegalArgumentException("entities to save cant be null");
    }
    return repository.saveAll(t);
  }

  /**
   * Borra la entidad que coincide con el id
   *
   * @param id Id de la entidad que quieres borrar
   * @throws IllegalArgumentException Si el id es null o está vacío
   */
  default void deleteById(long id, JpaRepository<T, Long> repository) {
    repository.deleteById(id);
  }

  /**
   * Borra las entidades que coinciden con el id
   *
   * @param ids Lista de Ids de las entidades que quieres borrar
   * @throws IllegalArgumentException Si el id es null
   */
  default void deleteAllById(List<Long> ids, JpaRepository<T, Long> repository) {
    if (ids == null) {
      var errorMessage = String.format("%s cant be null", "ids");
      throw new IllegalArgumentException(errorMessage);
    }
    repository.deleteAllById(ids);
  }
}
