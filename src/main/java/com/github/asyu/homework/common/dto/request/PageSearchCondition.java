package com.github.asyu.homework.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PageSearchCondition {

  private static final int DEFAULT_PAGE = 1;

  private static final int DEFAULT_SIZE = 5;

  private static final int MAX_SIZE = 50;

  private int page;

  private int size;

  public void setPage(int page) {
    this.page = page <= 0 ? DEFAULT_PAGE : page;
  }

  public int getPage() {
    return page <= 0 ? DEFAULT_PAGE : page;
  }

  public void setSize(int size) {
    this.size = (size <= 0 || size > MAX_SIZE) ? DEFAULT_SIZE : size;
  }

  public int getSize() {
    return (size <= 0 || size > MAX_SIZE) ? DEFAULT_SIZE : size;
  }

}
