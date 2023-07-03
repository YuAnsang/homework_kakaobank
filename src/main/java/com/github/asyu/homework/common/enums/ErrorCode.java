package com.github.asyu.homework.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

  COMMON("COMMON-001", "요청 처리에 실패하였습니다."),

  INVALID_PARAMETER("VALID-001", "잘못된 요청 값입니다. 파라미터를 확인해주세요."),

  OUTER_API_COMMUNICATION_FAILURE("OUTER-001", "외부와의 통신에 실패하였습니다. 잠시 후 다시 시도해주세요.");

  private final String code;

  private final String message;

}
