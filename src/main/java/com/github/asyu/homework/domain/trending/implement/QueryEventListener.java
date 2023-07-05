package com.github.asyu.homework.domain.trending.implement;

import com.github.asyu.homework.common.aop.DistributedLock;
import com.github.asyu.homework.common.event.QueryEvent;
import com.github.asyu.homework.common.exception.EntityNotExistsException;
import com.github.asyu.homework.domain.trending.persistence.dao.TrendingDao;
import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueryEventListener {

  private final TrendingDao trendingDao;

  @DistributedLock(key = "#event.query()")
  @EventListener
  public void subscribeQueryEvent(QueryEvent event) {
    Trending trending = getTrending(event.query());
    trendingDao.save(trending);
  }

  private Trending getTrending(String query) {
    try {
      Trending trending = trendingDao.findByQuery(query);
      log.info("trending id : {}, query : {}, count : {}", trending.getId(), trending.getQuery(), trending.getQueryCount());
      trending.increaseQueryCount();
      return trending;
    } catch (EntityNotExistsException e) {
      return new Trending(query, 1);
    }
  }
}
