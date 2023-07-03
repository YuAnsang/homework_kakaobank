package com.github.asyu.homework.domain.blog.implement.search.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record KakaoBlogResponse(
    Meta meta,
    List<Document> documents
) {

  public record Meta(
      @JsonProperty("total_count") Integer totalCount,
      @JsonProperty("pageable_count") Integer pageableCount,
      @JsonProperty("is_end") Boolean isEnd
  ) {

  }

  public record Document(
      String title,
      String contents,
      String url,
      String blogname,
      String thumbnail,
      String datetime
  ) {

  }
}
