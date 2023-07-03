package com.github.asyu.homework.domain.blog.implement.search.kakao;

import com.github.asyu.homework.infra.http.IRetrofitSpec;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KaKaoApiSpec extends IRetrofitSpec {

  @GET("/v2/search/blog")
  Call<KakaoBlogResponse> searchBlog(@Header("Authorization") String key, @Query("query") String query, @Query("sort") String sort, @Query("page") int page, @Query("size") int size);

}
