package com.github.asyu.homework.domain.blog.implement.search;

import com.github.asyu.homework.common.exception.CommunicationFailureException;
import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlogSearchAdapter {

  private final List<IBlogSearchEngine> searchEngines;

  public BlogResponse search(BlogSearchCondition condition) {
    try {
      return searchEngines.get(0).search(condition);
    } catch (CallNotPermittedException | CommunicationFailureException e) {
      log.warn("first search engine communication failure : " + e.getMessage());
      return searchEngines.get(1).search(condition);
    }
  }

}
