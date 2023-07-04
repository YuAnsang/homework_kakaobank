package com.github.asyu.homework.domain.trending.persistence.dao;

import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import com.github.asyu.homework.domain.trending.persistence.repository.TrendingRepository;
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
//    return trendingRepository.findByQuery(query)
//        .orElseThrow(); // TODO 여기서 던지고, 위에서 잡아서 Entity를 생성할지 아래 처럼 넘기는게 좋을지 고민 필요 (다음 commit에서 확정 후 주석 삭제한다.)
    return trendingRepository.findByQuery(query)
        .orElse(new Trending(query, 0));
  }

}
