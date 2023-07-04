package com.github.asyu.homework.domain.blog.implement.search.naver;

import java.util.List;

public record NaverBlogResponse(
    Integer total,
    Integer start,
    Integer display,
    String lastBuildDate,
    List<NaverBlogItem> items
) {
  public record NaverBlogItem(
      String title,
      String link,
      String description,
      String bloggername,
      String bloggerlink,
      String postdate
  ) {

  }
}
