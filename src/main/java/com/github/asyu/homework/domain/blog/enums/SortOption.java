package com.github.asyu.homework.domain.blog.enums;

public enum SortOption {

  ACCURACY,
  NEWEST,
  NULL;


  public String getKakaoCode() {
    switch (this) {
      case ACCURACY -> {
        return "accuracy";
      }
      case NEWEST -> {
        return "recency";
      }
      default -> {
        return null;
      }
    }
  }

  public String getNaverCode() {
    switch (this) {
      case ACCURACY -> {
        return "sim";
      }
      case NEWEST -> {
        return "date";
      }
      default -> {
        return null;
      }
    }
  }
}
