package com.github.asyu.homework.domain.trending.persistence.repository;

import com.github.asyu.homework.domain.trending.dto.response.TrendingDto;
import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrendingRepository extends JpaRepository<Trending, String> {

  Optional<Trending> findByQuery(String query);

  @Query("select new com.github.asyu.homework.domain.trending.dto.response.TrendingDto(t.query, t.queryCount) from Trending t order by t.queryCount desc limit 10")
  List<TrendingDto> findTop10ByOrderByQueryCountDesc();

}
