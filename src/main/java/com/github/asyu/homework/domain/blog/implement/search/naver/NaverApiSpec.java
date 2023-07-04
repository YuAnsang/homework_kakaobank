package com.github.asyu.homework.domain.blog.implement.search.naver;

import com.github.asyu.homework.infra.http.IRetrofitSpec;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NaverApiSpec extends IRetrofitSpec {

  @GET("/v1/search/blog.json")
  Call<NaverBlogResponse> searchBlog(
      @Header("X-Naver-Client-Id") String clientId, @Header("X-Naver-Client-Secret") String secret,
      @Query("query") String query, @Query("sort") String sort, @Query("start") int page, @Query("display") int size
  );

}
