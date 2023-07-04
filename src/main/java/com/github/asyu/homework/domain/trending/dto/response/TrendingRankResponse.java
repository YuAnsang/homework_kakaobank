package com.github.asyu.homework.domain.trending.dto.response;

import java.util.List;

public record TrendingRankResponse(
    List<TrendingDto> rank
) {
}
