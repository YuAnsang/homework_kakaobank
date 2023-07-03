package com.github.asyu.homework.domain.blog.implement.search;

import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;

public interface IBlogSearchEngine {

  // TODO 카카오와 네이버는 request spec이 동일하다고 봐도 무방한데, 다른 애들의 경우 달라진다면 어떻게 해야되는지 고려 필요.
  BlogResponse search(BlogSearchCondition condition);

}
