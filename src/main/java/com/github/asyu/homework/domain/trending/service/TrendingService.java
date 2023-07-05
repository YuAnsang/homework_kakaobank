package com.github.asyu.homework.domain.trending.service;

import com.github.asyu.homework.domain.trending.dto.response.TrendingRankResponse;
import com.github.asyu.homework.domain.trending.persistence.dao.TrendingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TrendingService {

  private final TrendingDao trendingDao;

  @Transactional(readOnly = true)
  public TrendingRankResponse getTop10Trends() {
    return new TrendingRankResponse(trendingDao.findTop10());
  }
}
