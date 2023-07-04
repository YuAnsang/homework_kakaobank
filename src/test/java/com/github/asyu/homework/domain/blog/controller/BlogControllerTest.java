package com.github.asyu.homework.domain.blog.controller;

import static com.github.asyu.homework.common.SpringProfiles.TEST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.asyu.homework.common.enums.ErrorCode;
import com.github.asyu.homework.domain.blog.dto.request.BlogSearchCondition;
import com.github.asyu.homework.domain.blog.enums.SortOption;
import com.github.asyu.homework.domain.blog.implement.event.QueryEventPublisher;
import com.github.asyu.homework.domain.blog.implement.search.kakao.KaKaoApiSpec;
import com.github.asyu.homework.domain.blog.implement.search.kakao.KakaoBlogResponse;
import com.github.asyu.homework.domain.blog.implement.search.kakao.KakaoBlogResponse.Document;
import com.github.asyu.homework.domain.blog.implement.search.kakao.KakaoBlogResponse.Meta;
import com.github.asyu.homework.domain.blog.implement.search.kakao.KakaoBlogSearchEngine;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import retrofit2.Call;
import retrofit2.mock.Calls;

@ActiveProfiles(TEST)
@AutoConfigureMockMvc
@SpringBootTest
class BlogControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private KakaoBlogSearchEngine kakaoBlogSearchEngine;

  @Mock
  private KaKaoApiSpec kaKaoApiSpec;

  @MockBean
  private QueryEventPublisher queryEventPublisher;

  @BeforeEach
  public void setup() {
    ReflectionTestUtils.setField(kakaoBlogSearchEngine, "apiSpec", kaKaoApiSpec);
  }

  @DisplayName("검색 조건으로 블로그 검색을 한다 -> 성공")
  @MethodSource
  @ParameterizedTest
  void search_blog_success(BlogSearchCondition condition) throws Exception {
    // Given
    int totalPage = 3000;
    KakaoBlogResponse response = new KakaoBlogResponse(
        new Meta(totalPage, 815, false),
        createDocuments(condition.getSize())
    );
    Call<KakaoBlogResponse> mockCall = Calls.response(response);
    doNothing().when(queryEventPublisher).publish(condition.getQuery());  // 인기 검색어 저장은 다른 관심사이기 때문에 별도로 테스트한다.
    when(kaKaoApiSpec.searchBlog(any(String.class), any(String.class), any(), any(Integer.class), any(Integer.class))).thenReturn(mockCall);

    // When & Then
    mockMvc.perform(get("/apis/v1/blogs")
            .param("query", condition.getQuery())
            .param("sort", condition.getSort().name())
            .param("page", String.valueOf(condition.getPage()))
            .param("size", String.valueOf(condition.getSize()))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.pageItem").exists())
        .andExpect(jsonPath("$.pageItem.page").value(condition.getPage()))
        .andExpect(jsonPath("$.pageItem.size").value(condition.getSize()))
        .andExpect(jsonPath("$.pageItem.totalCount").value(totalPage))
        .andExpect(jsonPath("$.items").isNotEmpty())
        .andExpect(jsonPath("$.items.size()").value(condition.getSize()))
    ;
  }

  private static Stream<Arguments> search_blog_success() {
    return Stream.of(
        Arguments.of(new BlogSearchCondition("테스트", SortOption.NULL, 1, 5)),
        Arguments.of(new BlogSearchCondition("테스트", SortOption.ACCURACY, 1, 15)),
        Arguments.of(new BlogSearchCondition("테스트", SortOption.NEWEST, 1, 16))
    );
  }

  private List<Document> createDocuments(int size) {
    List<Document> documents = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Document document = new Document(
          "테스트 타이틀" + i,
          "테스트 컨텐츠" + i,
          "http://localhost:8080",
          "테스트 블로그명" + i,
          "",
          OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
      );
      documents.add(document);
    }
    return documents;
  }

  @DisplayName("검색 조건으로 블로그 검색을 한다 -> 실패 (Query가 null인 경우)")
  @Test
  void search_blog_failure_cause_query_is_null() throws Exception {
    // Given
    BlogSearchCondition condition = new BlogSearchCondition(null, SortOption.NULL, 1, 5);

    // When & Then
    mockMvc.perform(get("/apis/v1/blogs")
            .param("query", condition.getQuery())
            .param("sort", condition.getSort().name())
            .param("page", String.valueOf(condition.getPage()))
            .param("size", String.valueOf(condition.getSize()))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getCode()))
        .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PARAMETER.getMessage()))
    ;
  }

  // TODO 1. Retrofit에서 isSuccess false인 경우 추가
  // TODO 2. Retrofit에서 IOException 발생하는 경우 추가

}
