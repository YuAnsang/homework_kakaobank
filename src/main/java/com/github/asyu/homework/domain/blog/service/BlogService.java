package com.github.asyu.homework.domain.blog.service;

import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.implement.event.QueryEventPublisher;
import com.github.asyu.homework.domain.blog.implement.search.BlogSearchAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

  private final BlogSearchAdapter blogSearchAdapter;

  private final QueryEventPublisher queryEventPublisher;

  public BlogResponse searchBlog(BlogSearchCondition condition) {
    BlogResponse response = blogSearchAdapter.search(condition);
    if (response.items().size() > 0) {
      queryEventPublisher.publish(condition.getQuery());
    }
    return response;
  }

}
