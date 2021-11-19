package com.xbidi.spring.content.shared.interfaces.service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface Crud<T> {

  /**
   * Devuelve la entidad filtrando por el id
   *
   * @param id Id sobre el que se filtra
   * @return La entidad encontrada
   * @throws EntityNotFoundException Si no encuentra la entidad con el id
   * @throws IllegalArgumentException Si el id es null o está vacío
   */
  T findById(long id);

  /**
   * Devuelve la entidad filtrando por el id, en el caso de no encontrarla devuelve null
   *
   * @param id Id sobre el que se filtra
   * @return La entidad encontrada o null
   * @throws IllegalArgumentException Si el id es null o está vacío
   */
  T findByIdOrNull(long id);

  /**
   * Devuelve todas las entidades que encuentre con los ids
   *
   * @param ids Ids sobre los que filtrar
   * @return La lista con las entidades encontradas
   * @throws IllegalArgumentException Si el ids es null
   */
  List<T> findAllByIds(List<Long> ids);

  /**
   * Guarda la entidad, si no existía la crea y si existía la actualiza
   *
   * @param t Entidad que quieres guardar
   * @return Devuelve la entidad creada o actualizada
   * @throws IllegalArgumentException Si la entidad es null
   */
  T save(T t);

  /**
   * Guarda todas las entidades, las que no existen las crea y las que existen las actualiza
   *
   * @param t Lista con las entidades que quieres crear o actualizar
   * @return Devuelve la lista de las entidades creadas o actualizadas
   * @throws IllegalArgumentException Si la lista de entidades es null
   */
  List<T> saveAll(List<T> t);

  /**
   * Borra la entidad que coincide con el id
   *
   * @param id Id de la entidad que quieres borrar
   * @throws IllegalArgumentException Si el id es null o está vacío
   */
  void deleteById(long id);

  /**
   * Borra las entidades que coinciden con el id
   *
   * @param ids Lista de Ids de las entidades que quieres borrar
   * @throws IllegalArgumentException Si el id es null
   */
  void deleteAllById(List<Long> ids);
}
