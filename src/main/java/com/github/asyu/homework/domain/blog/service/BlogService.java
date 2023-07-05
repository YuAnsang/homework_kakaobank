package com.github.asyu.homework.domain.blog.service;

import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.enums.BlogType;
import com.github.asyu.homework.domain.blog.implement.event.QueryEventPublisher;
import com.github.asyu.homework.domain.blog.implement.search.BlogSearchEngineWrapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogService {

  private final BlogSearchEngineWrapper searchEngineWrapper;

  private final QueryEventPublisher queryEventPublisher;

  @CircuitBreaker(name = BlogType.UPPER_KAKAO, fallbackMethod = "searchFromNaver")
  public BlogResponse searchBlog(BlogSearchCondition condition) {
    BlogResponse response = searchEngineWrapper.searchFromKakao(condition);
    if (response.items().size() > 0) {
      queryEventPublisher.publish(condition.getQuery());
    }
    return response;
  }

  private BlogResponse searchFromNaver(BlogSearchCondition condition, Throwable t) {
    log.warn("kakaoEngine search failure : {}", t.getMessage());
    return searchEngineWrapper.searchFromNaver(condition);
  }

}
