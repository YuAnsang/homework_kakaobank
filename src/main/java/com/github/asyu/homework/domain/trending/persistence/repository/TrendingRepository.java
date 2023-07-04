package com.github.asyu.homework.domain.trending.persistence.repository;

import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrendingRepository extends JpaRepository<Trending, String> {

  Optional<Trending> findByQuery(String query);

}
