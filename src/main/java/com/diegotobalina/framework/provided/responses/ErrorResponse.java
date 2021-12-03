package com.diegotobalina.framework.provided.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/** @author diegotobalina created on 13/08/2020 */
@Slf4j
@Getter
@ToString(exclude = {"exception"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {

  private String id;
  private Date timestamp;
  private String fullMessage;
  private String method;
  private String message;
  private Integer status;
  @JsonIgnore private transient Throwable exception;

  public ErrorResponse(Throwable exception, Integer status) {
    this.id = UUID.randomUUID().toString();
    this.exception = exception;
    this.timestamp = new Date();
    this.fullMessage = getFullMessageString();
    this.method = getMethod();
    this.message = getMessageString();
    this.status = status;
  }

  public ErrorResponse(Throwable exception) {
    this.id = UUID.randomUUID().toString();
    this.exception = exception;
    this.timestamp = new Date();
    this.fullMessage = getFullMessageString();
    this.method = getMethod();
    this.message = getMessageString();
  }

  private String getMessageString() {
    return this.exception.getMessage();
  }

  private String getFullMessageString() {
    String methodName = getMethod();
    String rootCause = ExceptionUtils.getMessage(exception);
    return String.format("%s, %s", methodName, rootCause);
  }

  private String getMethod() {
    if (exception == null || exception.getStackTrace().length == 0) return "";
    String[] classNameSplit = exception.getStackTrace()[0].getClassName().split("\\.");
    String className = classNameSplit[classNameSplit.length - 1];
    String methodName = exception.getStackTrace()[0].getMethodName();
    return String.format("%s.%s", className, methodName);
  }

  public ErrorResponse printMessage() {
    log.warn(String.format("Exception handled: %s", getMessageString()));
    this.exception.printStackTrace();
    return this;
  }
}
