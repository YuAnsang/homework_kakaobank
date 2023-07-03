package com.github.asyu.homework.domain.blog.implement.search.kakao;

import com.github.asyu.homework.common.dto.response.PageItem;
import com.github.asyu.homework.common.exception.CommunicationFailureException;
import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.implement.search.IBlogSearchEngine;
import com.github.asyu.homework.domain.blog.implement.maper.BlogMapper;
import com.github.asyu.homework.infra.http.RetrofitSpecFactory;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@Component
public class KakaoBlogSearchEngine implements IBlogSearchEngine {

  private final String apiKey;

  private final KaKaoApiSpec apiSpec;

  public KakaoBlogSearchEngine(@Value("${kakao.api.url}") String baseUrl,
      @Value("${kakao.api.key}") String apiKey) {
    this.apiSpec = RetrofitSpecFactory.createRequestClient(baseUrl, KaKaoApiSpec.class);
    this.apiKey = "KakaoAK " + apiKey;
  }

  @Override
  public BlogResponse search(BlogSearchCondition condition) {
    try {
      int page = condition.getPage();
      int size = condition.getSize();
      Call<KakaoBlogResponse> call = apiSpec.searchBlog(
          apiKey,
          condition.getQuery(),
          condition.getSort().getKakaoCode(),
          page,
          size
      );

      Response<KakaoBlogResponse> response = call.execute();
      if (!response.isSuccessful()) {
        String errorBody = response.errorBody() == null ? "" : response.errorBody().string();
        throw new CommunicationFailureException("Retrofit Response Failure. Error Response : " + errorBody);
      }

      KakaoBlogResponse body = response.body();

      assert body != null;
      return new BlogResponse(BlogMapper.INSTANCE.toItems(body.documents()), new PageItem(page, size, body.meta().totalCount()));
    } catch (IOException e) {
      throw new CommunicationFailureException(e);
    }
  }

}
