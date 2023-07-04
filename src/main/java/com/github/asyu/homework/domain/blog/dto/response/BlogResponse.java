package com.github.asyu.homework.domain.blog.dto.response;

import com.github.asyu.homework.common.dto.response.PageItem;
import com.github.asyu.homework.domain.blog.enums.BlogType;
import java.time.LocalDate;
import java.util.List;

public record BlogResponse(
    BlogType type,
    List<Item> items,
    PageItem pageItem
) {

  public record Item(
      String title,
      String contents,
      String link,
      String blogName,
      LocalDate createdDate
  ) {

  }
}
