package com.diegotobalina.framework.provided;

import org.springframework.beans.factory.annotation.Value;

public abstract class Constants {

  // Constantes
  @Value("${authorization.header}")
  public static String AUTHORIZATION_HEADER;

  @Value("${anonymous.user.email}")
  public static String ANONYMOUS_USER;

  public static final String LOGS_REQUEST_METHOD = "Request method: %s";
  public static final String LOGS_REQUEST_URL = "Request url: %s";
  public static final String LOGS_REQUEST_PARAMS = "Request params: %s";
  public static final String LOGS_REQUEST_BODY = "Request body: %s";
  public static final String LOGS_REQUEST_HEADERS = "Request headers: %s";
  public static final String LOGS_RESPONSE_CODE = "Response code: %s";
  public static final String LOGS_RESPONSE_HEADERS = "Response headers: %s";
  public static final String LOGS_RESPONSE_BODY = "Response body: %s";
  public static final String LOGS_TOTAL_REQUEST_TIME = "Total request time: %s ms";

  @Value("${multitenant.default.db}")
  public static String MULTITENANT_DEFAULT_DB;

  @Value("${multitenant.default.header}")
  public static String MULTITENANT_HEADER;

  private Constants() {}
}
