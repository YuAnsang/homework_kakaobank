package com.github.asyu.homework.common.dto.response;

import com.github.asyu.homework.common.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

  private String code;

  private String message;

   public static ErrorResponse of(ErrorCode errorCode) {
     return ErrorResponse.of(errorCode.getCode(), errorCode.getMessage());
   }

   public static ErrorResponse of(String errorCode, String message) {
     return new ErrorResponse(errorCode, message);
   }
}
