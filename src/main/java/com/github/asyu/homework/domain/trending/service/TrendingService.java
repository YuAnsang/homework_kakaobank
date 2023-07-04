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
    // TODO "인기" 검색어라는게 단순히 전체 count만 보는게 아니라, 특정 기간(?)내에 많이 검색된 애들로 되어야 할 것 같은데, 이게 가능한지는 고민 필요
    return new TrendingRankResponse(trendingDao.findTop10());
  }
}
