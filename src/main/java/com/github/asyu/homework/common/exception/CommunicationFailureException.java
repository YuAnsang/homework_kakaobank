package com.github.asyu.homework.common.exception;

public class CommunicationFailureException extends BusinessException {

  public CommunicationFailureException(String message) {
    super(message);
  }

  public CommunicationFailureException(Throwable cause) {
    super(cause);
  }

}
