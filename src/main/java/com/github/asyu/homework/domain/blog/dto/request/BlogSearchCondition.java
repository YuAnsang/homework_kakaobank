package com.github.asyu.homework.domain.blog.dto.request;

import com.github.asyu.homework.common.dto.request.PageSearchCondition;
import com.github.asyu.homework.domain.blog.enums.SortOption;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogSearchCondition extends PageSearchCondition {

  @NotBlank
  private String query;

  private SortOption sort = SortOption.NULL;

}
