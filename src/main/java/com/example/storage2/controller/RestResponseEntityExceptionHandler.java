package com.example.storage2.controller;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
    extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value
      = {IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleConflict(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Wrong argument!";
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(value
      = {NoSuchElementException.class})
  protected ResponseEntity<Object> handleConflict3(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "There is no elements";
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(value
      = {HibernateException.class, org.postgresql.util.PSQLException.class, SQLException.class,
      DataIntegrityViolationException.class, RuntimeException.class})
  protected ResponseEntity<Object> handleConflict4(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Database error. Wrong input!";
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.CONFLICT, request);
  }
}