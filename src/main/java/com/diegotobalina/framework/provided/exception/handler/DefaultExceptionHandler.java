package com.diegotobalina.framework.provided.exception.handler;

import com.diegotobalina.framework.provided.responses.ErrorResponse;
import com.diegotobalina.framework.provided.exception.exception.BadRequestException;
import com.diegotobalina.framework.provided.exception.exception.EntityNotValidException;
import com.diegotobalina.framework.provided.exception.exception.ForbiddenTenantException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

  @ResponseBody
  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      String field = error.getField();
      String defaultMessage = error.getDefaultMessage();
      errors.add(String.format("%s:%s", field, defaultMessage));
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      String field = error.getObjectName();
      String defaultMessage = error.getDefaultMessage();
      errors.add(String.format("%s:%s", field, defaultMessage));
    }
    var arrayToString = errors.toString();
    var cleanedMessage = arrayToString.substring(1, arrayToString.length() - 1);
    return new ErrorResponse(new Exception(cleanedMessage), HttpStatus.BAD_REQUEST.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({ForbiddenTenantException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse forbiddenTenantExceptionF(ForbiddenTenantException ex) {
    return new ErrorResponse(ex, HttpStatus.FORBIDDEN.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse constraintViolationException(ConstraintViolationException ex) {
    return new ErrorResponse(ex, HttpStatus.BAD_REQUEST.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({BadRequestException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse badRequestException(BadRequestException ex) {
    return new ErrorResponse(ex, HttpStatus.BAD_REQUEST.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({EntityNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse entityNotValidException(EntityNotValidException ex) {
    return new ErrorResponse(ex, HttpStatus.BAD_REQUEST.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({EntityNotFoundException.class})
  @ResponseStatus(HttpStatus.OK)
  private ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ResponseBody
  @ExceptionHandler({UndeclaredThrowableException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse undeclaredThrowableException(UndeclaredThrowableException ex) {
    return new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse exception(Exception ex) {
    return new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({InsufficientAuthenticationException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  private ErrorResponse insufficientAuthenticationException(
      InsufficientAuthenticationException ex) {
    return new ErrorResponse(ex, HttpStatus.UNAUTHORIZED.value()).printMessage();
  }

  @ResponseBody
  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse accessDeniedException(AccessDeniedException ex) {
    return new ErrorResponse(ex, HttpStatus.FORBIDDEN.value()).printMessage();
  }
}
