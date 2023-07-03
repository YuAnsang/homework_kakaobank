package com.github.asyu.homework.domain.blog.mapper;

import com.github.asyu.homework.domain.blog.dto.response.BlogResponse.Item;
import com.github.asyu.homework.domain.blog.implement.search.kakao.KakaoBlogResponse.Document;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogMapper {

  BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

  List<Item> toItems(List<Document> documents);

  @Mapping(source = "blogname", target = "blogName")
  @Mapping(source = "url", target = "link")
  @Mapping(source = "datetime", target = "createdDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  Item toItem(Document document);
}
