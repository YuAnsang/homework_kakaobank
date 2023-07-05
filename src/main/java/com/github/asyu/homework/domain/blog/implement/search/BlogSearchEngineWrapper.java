package com.github.asyu.homework.domain.blog.implement.search;

import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.enums.BlogType;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BlogSearchEngineWrapper {

  private final List<IBlogSearchEngine> searchEngines;

  @Retry(name = BlogType.UPPER_KAKAO)
  public BlogResponse searchFromKakao(BlogSearchCondition condition) {
    return searchEngines.get(0).search(condition);
  }

  @Retry(name = BlogType.UPPER_NAVER)
  public BlogResponse searchFromNaver(BlogSearchCondition condition) {
    return searchEngines.get(1).search(condition);
  }

}
