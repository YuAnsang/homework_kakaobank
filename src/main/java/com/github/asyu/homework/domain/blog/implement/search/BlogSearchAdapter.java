package com.github.asyu.homework.domain.blog.implement.search;

import com.github.asyu.homework.common.exception.CommunicationFailureException;
import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BlogSearchAdapter {

  private final List<IBlogSearchEngine> searchEngines;

  public BlogResponse search(BlogSearchCondition condition) {
    // TODO 일단 초간단하게 구현해놓고 circuitBreaker 같은거 고민 필요.
    try {
      return searchEngines.get(0).search(condition);
    } catch (CommunicationFailureException e) {
      return searchEngines.get(1).search(condition);
    }
  }

}
