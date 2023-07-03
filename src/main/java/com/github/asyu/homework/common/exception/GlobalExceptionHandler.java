package com.github.asyu.homework.common.exception;

import com.github.asyu.homework.common.dto.response.ErrorResponse;
import com.github.asyu.homework.common.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public ErrorResponse handleBindException(BindException e) {
    BindingResult bindingResult = e.getBindingResult();

    StringBuilder sb = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      sb.append("field: [");
      sb.append(fieldError.getField());
      sb.append("]");
      sb.append(" input value: [");
      sb.append(fieldError.getRejectedValue());
      sb.append("]");
      sb.append(" message: [");
      sb.append(fieldError.getDefaultMessage());
      sb.append("]");
    }

    log.error("Validation Exception : {}", sb);
    return ErrorResponse.of(ErrorCode.INVALID_PARAMETER);
  }

  // TODO 500 던지는거에 대해서 고민필요
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BusinessException.class)
  public ErrorResponse handleRuntimeException(BusinessException e) {
    log.error(e.getMessage(), e);
    return ErrorResponse.of(ErrorCode.COMMON);
  }

  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  @ExceptionHandler(CommunicationFailureException.class)
  public ErrorResponse handleRuntimeException(CommunicationFailureException e) {
    log.error(e.getMessage(), e);
    return ErrorResponse.of(ErrorCode.OUTER_API_COMMUNICATION_FAILURE);
  }
}
