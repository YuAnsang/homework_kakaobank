package com.github.asyu.homework.domain.trending.persistence.repository;

import com.github.asyu.homework.domain.trending.dto.response.TrendingDto;
import com.github.asyu.homework.domain.trending.persistence.entity.TrendingLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrendingLogRepository extends JpaRepository<TrendingLog, String> {

  @Query(value = """
        SELECT new com.github.asyu.homework.domain.trending.dto.response.TrendingDto(t.query, count(t))
        FROM TrendingLog t
        WHERE t.id IN (SELECT t2.id FROM TrendingLog t2 ORDER BY t2.createdAt DESC LIMIT ?1)
        GROUP BY t.query
        ORDER BY count(t) DESC, MAX(t.createdAt) DESC
        LIMIT 10
      """)
  List<TrendingDto> findTop10(Integer slidingWindowSize);
}
