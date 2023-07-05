package com.github.asyu.homework.domain.blog.implement.search.naver;

import com.github.asyu.homework.common.dto.response.PageItem;
import com.github.asyu.homework.common.exception.CommunicationFailureException;
import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.enums.BlogType;
import com.github.asyu.homework.domain.blog.implement.maper.BlogMapper;
import com.github.asyu.homework.domain.blog.implement.search.IBlogSearchEngine;
import com.github.asyu.homework.infra.http.RetrofitSpecFactory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@Order(2)
@Component
public class NaverBlogSearchEngine implements IBlogSearchEngine {

  private final String clientId;

  private final String secret;

  private final NaverApiSpec apiSpec;

  public NaverBlogSearchEngine(@Value("${naver.api.url}") String baseUrl,
      @Value("${naver.api.client-id}") String clientId, @Value("${naver.api.secret}") String secret) {
    this.apiSpec = RetrofitSpecFactory.createRequestClient(baseUrl, NaverApiSpec.class);
    this.clientId = clientId;
    this.secret = secret;
  }

  @CircuitBreaker(name = "naver", fallbackMethod = "defaultFallback")
  @Retry(name = "naver")
  @Override
  public BlogResponse search(BlogSearchCondition condition) {
    try {
      int page = condition.getPage();
      int size = condition.getSize();
      Call<NaverBlogResponse> call = apiSpec.searchBlog(
          clientId, secret,
          condition.getQuery(),
          condition.getSort().getNaverCode(),
          page,
          size
      );

      Response<NaverBlogResponse> response = call.execute();
      if (!response.isSuccessful()) {
        String errorBody = response.errorBody() == null ? "" : response.errorBody().string();
        throw new CommunicationFailureException("Naver Response Failure. Error Response : " + errorBody);
      }

      NaverBlogResponse body = response.body();

      assert body != null;
      return new BlogResponse(BlogType.NAVER, BlogMapper.INSTANCE.toItemsForNaver(body.items()), new PageItem(page, size, body.total()));
    } catch (IOException e) {
      throw new CommunicationFailureException("Naver communication failure", e);
    }
  }

  private BlogResponse defaultFallback(BlogSearchCondition condition, Throwable t) {
    // TODO Exception이 발생하는게 아니라 Cache같은게 있어서 BlogResponse를 Return 할 수 있으면 좋을거같은데...
    throw new CommunicationFailureException("All Search Engine communication failure. query : " + condition.getQuery(), t);
  }
}
