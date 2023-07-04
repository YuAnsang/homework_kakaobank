package com.github.asyu.homework.domain.trending.persistence.dao;

import com.github.asyu.homework.common.exception.EntityNotExistsException;
import com.github.asyu.homework.domain.trending.dto.response.TrendingDto;
import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import com.github.asyu.homework.domain.trending.persistence.repository.TrendingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TrendingDao {

  private final TrendingRepository trendingRepository;

  public Trending save(Trending trending) {
    return trendingRepository.save(trending);
  }

  public Trending findByQuery(String query) {
    return trendingRepository.findByQuery(query)
        .orElseThrow(() -> new EntityNotExistsException(String.format("%s(query) is not exists in trending table.", query)));
  }

  public List<TrendingDto> findTop10() {
    return trendingRepository.findTop10ByOrderByQueryCountDesc();
  }

}
