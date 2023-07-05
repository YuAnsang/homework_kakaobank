package com.github.asyu.homework.domain.trending.dto.response;

public record TrendingDto(
    String query,
    Long queryCount
) {
}
