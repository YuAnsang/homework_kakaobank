package com.github.asyu.homework.domain.blog.controller;

import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.dto.response.BlogResponse;
import com.github.asyu.homework.domain.blog.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/apis/v1")
@RestController
public class BlogController {

  private final BlogService blogService;

  @GetMapping("/blogs")
  public BlogResponse searchBlog(@Valid BlogSearchCondition condition) {
    return blogService.searchBlog(condition);
  }

}
