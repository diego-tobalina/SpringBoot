package com.diegotobalina.framework.core.api.response;

public interface EntityMetadata {

  /**
   * @return Devuelve la ruta para recuperar la entidad
   */
  String getPath();

  /**
   * @return Devuelve el id del tenant al que pertenece
   */
  String getTenantId();
}
