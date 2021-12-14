package com.diegotobalina.framework.provided;

import org.springframework.beans.factory.annotation.Value;

public abstract class Constants {

  public static final String LOGS_REQUEST_METHOD = "Request method: %s";
  public static final String LOGS_REQUEST_URL = "Request url: %s";
  public static final String LOGS_REQUEST_PARAMS = "Request params: %s";
  public static final String LOGS_REQUEST_BODY = "Request body: %s";
  public static final String LOGS_REQUEST_HEADERS = "Request headers: %s";
  public static final String LOGS_RESPONSE_CODE = "Response code: %s";
  public static final String LOGS_RESPONSE_HEADERS = "Response headers: %s";
  public static final String LOGS_RESPONSE_BODY = "Response body: %s";
  public static final String LOGS_TOTAL_REQUEST_TIME = "Total request time: %s ms";

  @SuppressWarnings({"java:S1444", "java:S3008"})
  @Value("${authorization.header}")
  public static String AUTHORIZATION_HEADER;

  @SuppressWarnings({"java:S1444", "java:S3008"})
  @Value("${anonymous.user.email}")
  public static String ANONYMOUS_USER_EMAIL;

  @SuppressWarnings({"java:S1444", "java:S3008"})
  @Value("${multitenant.default.db}")
  public static String MULTITENANT_DEFAULT_DB;

  @SuppressWarnings({"java:S1444", "java:S3008"})
  @Value("${multitenant.default.header}")
  public static String MULTITENANT_HEADER;

  private Constants() {}
}
