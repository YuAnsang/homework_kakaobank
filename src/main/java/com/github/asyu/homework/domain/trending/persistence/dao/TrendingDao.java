package com.github.asyu.homework.domain.trending.persistence.dao;

import com.github.asyu.homework.common.exception.EntityNotExistsException;
import com.github.asyu.homework.domain.trending.dto.response.TrendingDto;
import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import com.github.asyu.homework.domain.trending.persistence.entity.TrendingLog;
import com.github.asyu.homework.domain.trending.persistence.repository.TrendingLogRepository;
import com.github.asyu.homework.domain.trending.persistence.repository.TrendingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TrendingDao {

  @Value("${trending.sliding.window.size:2000}")
  private Integer slidingWindowSize;

  private final TrendingRepository trendingRepository;

  private final TrendingLogRepository trendingLogRepository;

  public void save(Trending trending) {
    trendingRepository.save(trending);
    trendingLogRepository.save(new TrendingLog(trending.getQuery()));
  }

  public Trending findByQuery(String query) {
    return trendingRepository.findByQuery(query)
        .orElseThrow(() -> new EntityNotExistsException(String.format("%s(query) is not exists in trending table.", query)));
  }

  public List<TrendingDto> findTop10() {
    return trendingLogRepository.findTop10(slidingWindowSize);
  }

}
