package com.github.asyu.homework.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("v1")
        .pathsToMatch("/apis/v1/**")
        .build();
  }

  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("블로그 검색 API")
            .description("Open API를 활용한 블로그 검색 서비스")
            .version("0.0.1"));
  }

}
