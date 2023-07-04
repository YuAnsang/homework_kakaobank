package com.github.asyu.homework.domain.trending.controller;

import com.github.asyu.homework.domain.trending.dto.response.TrendingRankResponse;
import com.github.asyu.homework.domain.trending.service.TrendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/apis/v1")
@RestController
public class TrendingController {

  private final TrendingService trendingService;

  @GetMapping("/trends/top10")
  public TrendingRankResponse getTop10Trends() {
    return trendingService.getTop10Trends();
  }

}
