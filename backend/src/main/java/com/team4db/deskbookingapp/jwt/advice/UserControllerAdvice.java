package com.team4db.deskbookingapp.jwt.advice;

import com.team4db.deskbookingapp.jwt.exception.JWTException;
import com.team4db.deskbookingapp.jwt.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
@Slf4j
@RestControllerAdvice
public class UserControllerAdvice {
  @ExceptionHandler(value = JWTException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorMessage handleJWTException(JWTException ex, WebRequest request) {
    log.error("handle Jwt exception error");
    return new ErrorMessage(
        HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(), request.getDescription(false));
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorMessage handleJWTExceptionFromFilter(JWTException ex) {
    log.error("handle Jwt exception from filter error");
    return new ErrorMessage(
        HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(), ex.getMessage());
  }
}
