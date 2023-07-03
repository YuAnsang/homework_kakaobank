package com.github.asyu.homework.domain.blog.dto.response;

import com.github.asyu.homework.common.dto.response.PageItem;
import java.time.LocalDate;
import java.util.List;

public record BlogResponse(
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
