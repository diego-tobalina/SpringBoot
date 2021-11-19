package com.xbidi.spring.content.shared.interfaces.domain;

import com.xbidi.spring.exception.exception.EntityNotValidException;

public interface Valid {
  /**
   * En el caso de que la entidad no sea válida devuelve el primer error que ha encontrado, si la
   * entidad es válida devuelve null, implementar este método para sobreescribir esta funcionalidad
   */
  String getNotValidCause();

  default void checkIfCanBeInserted(Valid valid) throws EntityNotValidException {
    var notValidCause = valid.getNotValidCause();
    if (notValidCause != null) throw new EntityNotValidException(notValidCause);
  }
}
