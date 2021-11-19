package com.xbidi.spring.exception.handler;

import com.xbidi.spring.content.shared.output.ErrorResponse;
import com.xbidi.spring.exception.exception.BadRequestException;
import com.xbidi.spring.exception.exception.EntityNotValidException;
import com.xbidi.spring.exception.exception.ForbiddenTenantException;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  private ErrorResponse methodArgumentNotValidException(
      HttpServletRequest request,
      HttpServletResponse response,
      MethodArgumentNotValidException ex) {

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

    // arrayToString: [Example message 1, Example message 2]
    // cleanedMessage: Example message 1, Example message 2
    var arrayToString = errors.toString();
    var cleanedMessage = arrayToString.substring(1, arrayToString.length() - 1);

    return getPowerException(new Exception(cleanedMessage), HttpStatus.BAD_REQUEST.value());
  }

  @ResponseBody
  @ExceptionHandler({ForbiddenTenantException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse forbiddenTenantExceptionF(
      HttpServletRequest request, HttpServletResponse response, ForbiddenTenantException ex) {
    return getPowerException(ex, HttpStatus.FORBIDDEN.value());
  }

  @ResponseBody
  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse constraintViolationException(
      HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) {
    return getPowerException(ex, HttpStatus.BAD_REQUEST.value());
  }

  @ResponseBody
  @ExceptionHandler({BadRequestException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse badRequestException(
      HttpServletRequest request, HttpServletResponse response, BadRequestException ex) {
    return getPowerException(ex, HttpStatus.BAD_REQUEST.value());
  }

  @ResponseBody
  @ExceptionHandler({EntityNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse entityNotValidException(
      HttpServletRequest request, HttpServletResponse response, EntityNotValidException ex) {
    return getPowerException(ex, HttpStatus.BAD_REQUEST.value());
  }

  @ResponseBody
  @ExceptionHandler({EntityNotFoundException.class})
  @ResponseStatus(HttpStatus.OK)
  private ResponseEntity<Object> entityNotFoundException(
      HttpServletRequest request, HttpServletResponse response, EntityNotFoundException ex) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ResponseBody
  @ExceptionHandler({UndeclaredThrowableException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse undeclaredThrowableException(
      HttpServletRequest request, HttpServletResponse response, UndeclaredThrowableException ex) {
    return getPowerException(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @ResponseBody
  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse exception(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return getPowerException(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @ResponseBody
  @ExceptionHandler({InsufficientAuthenticationException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  private ErrorResponse insufficientAuthenticationException(
      HttpServletRequest request,
      HttpServletResponse response,
      InsufficientAuthenticationException ex) {
    return getPowerException(ex, HttpStatus.UNAUTHORIZED.value());
  }

  @ResponseBody
  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse accessDeniedException(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
    return getPowerException(ex, HttpStatus.FORBIDDEN.value());
  }

  private ErrorResponse getPowerException(Exception ex, int status) {
    var powerException = new ErrorResponse(ex, status);
    powerException.printMessage();
    return powerException;
  }
}