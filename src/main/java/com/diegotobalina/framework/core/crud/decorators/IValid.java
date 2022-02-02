package com.diegotobalina.framework.core.crud.decorators;

import com.diegotobalina.framework.core.exception.exception.EntityNotValidException;

public interface IValid {
    /**
     * En el caso de que la entidad no sea válida devuelve el primer error que ha encontrado, si la
     * entidad es válida devuelve null, implementar este método para sobreescribir esta funcionalidad
     */
    String getNotValidCause();

    default void checkIfCanBeInserted() throws EntityNotValidException {
        this.checkIfCanBeInserted(this);
    }

    default void checkIfCanBeInserted(IValid iValid) throws EntityNotValidException {
        var notValidCause = iValid.getNotValidCause();
        if (notValidCause != null) throw new EntityNotValidException(notValidCause);
    }
}
