package com.xdisx.customer.app.rest;

import com.xdisx.customer.api.exception.CustomerCreateException;
import com.xdisx.customer.api.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = CustomerController.class)
public class CustomerControllerExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({CustomerCreateException.class, CustomerNotFoundException.class})
  public ResponseEntity<Object> handleCustomerCreateException(
      WebRequest r, CustomerCreateException e) {
    return setResponse(r, e, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<Object> setResponse(
      WebRequest r, CustomerCreateException e, HttpStatus status) {
    ProblemDetail body = ProblemDetail.forStatusAndDetail(status, e.getMessage());

    return new ResponseEntity<>(body, status);
  }
}
