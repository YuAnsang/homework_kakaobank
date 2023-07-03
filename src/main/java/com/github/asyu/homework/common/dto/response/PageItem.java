package com.github.asyu.homework.common.dto.response;

public record PageItem(
    Integer page,
    Integer size,
    Integer totalCount
) {

}
