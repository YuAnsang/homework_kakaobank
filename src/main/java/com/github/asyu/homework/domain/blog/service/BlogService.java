package com.github.asyu.homework.domain.blog.service;

import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.implement.event.QueryEventPublisher;
import com.github.asyu.homework.domain.blog.implement.search.IBlogSearchEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

  // TODO 향후에는 engine을 직접 사용하지 않고, adapter를 따로 두는것으로 개선 필요
  private final IBlogSearchEngine blogSearchEngine;

  private final QueryEventPublisher queryEventPublisher;

  public BlogResponse searchBlog(BlogSearchCondition condition) {
    BlogResponse response = blogSearchEngine.search(condition);
    if (response.items().size() > 0) {
      queryEventPublisher.publish(condition.getQuery());
    }
    return response;
  }

}
