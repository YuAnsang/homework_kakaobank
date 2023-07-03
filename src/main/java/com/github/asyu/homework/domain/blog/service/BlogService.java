package com.github.asyu.homework.domain.blog.service;

import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.implement.search.IBlogSearchEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

  // TODO 향후에는 engine을 직접 사용하지 않고, adapter를 따로 두는것으로 개선 필요
  private final IBlogSearchEngine IBlogSearchEngine;

  public BlogResponse searchBlog(BlogSearchCondition condition) {
    // TODO 검색어에 대한 count increase 로직 필요
    return IBlogSearchEngine.search(condition);
  }
}
