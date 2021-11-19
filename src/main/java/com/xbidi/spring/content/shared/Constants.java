package com.xbidi.spring.content.shared;

public abstract class Constants {

  // Constantes
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String ANONYMOUS_USER = "anonymous";
  public static final String LOGS_REQUEST_METHOD = "Request method: %s";
  public static final String LOGS_REQUEST_URL = "Request url: %s";
  public static final String LOGS_REQUEST_PARAMS = "Request params: %s";
  public static final String LOGS_REQUEST_BODY = "Request body: %s";
  public static final String LOGS_REQUEST_HEADERS = "Request headers: %s";
  public static final String LOGS_RESPONSE_CODE = "Response code: %s";
  public static final String LOGS_RESPONSE_HEADERS = "Response headers: %s";
  public static final String LOGS_RESPONSE_BODY = "Response body: %s";
  public static final String LOGS_TOTAL_REQUEST_TIME = "Total request time: %s ms";
  public static final String MULTITENANT_DEFAULT_DB = "postgres";
  public static final String MULTITENANT_HEADER = "X-TenantID";

  // Errores genéricos de parámetros
  public static final String ERROR_PARAMETER_CANT_BE_NULL_OR_EMPTY =
      "%s cant be null or empty"; // 1 nombre del parámetro
  public static final String ERROR_PARAMETER_CANT_BE_NULL =
      "%s cant be null"; // 1 nombre del parámetro
  // Errores genéricos de entidades
  public static final String ERROR_ENTITY_NOT_FOUND_WITH_ID =
      "%s not found with id: %d"; // 1 nombre de la clase, 3 valor del campo
  public static final String ERROR_ENTITY_NOT_VALID =
      "%s not valid, cause: %s"; // 1 nombre de la entidad, 2 causa de que no sea válida
  // Errores de entrada de datos
  public static final String ERROR_ID_NOT_VALID = "El id no es válido";
  public static final String ERROR_NAME_FORMAT_NOT_VALID = "El formato del 'nombre' no es válido";
  public static final String ERROR_DESCRIPTION_FORMAT_NOT_VALID =
      "El formato de la 'descripción' no es válido";
  public static final String ERROR_INPUT_DTO_FORMAT_NOT_VALID =
      "El formato del 'InputDTO' no es válido";

  private Constants() {}
}
