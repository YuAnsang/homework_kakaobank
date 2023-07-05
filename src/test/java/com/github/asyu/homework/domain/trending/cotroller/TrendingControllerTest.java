package com.github.asyu.homework.domain.trending.cotroller;

import static com.github.asyu.homework.common.SpringProfiles.TEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import com.github.asyu.homework.domain.trending.persistence.entity.TrendingLog;
import com.github.asyu.homework.domain.trending.persistence.repository.TrendingLogRepository;
import com.github.asyu.homework.domain.trending.persistence.repository.TrendingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(TEST)
@AutoConfigureMockMvc
@SpringBootTest
class TrendingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TrendingRepository trendingRepository;

  @Autowired
  private TrendingLogRepository trendingLogRepository;

  @BeforeEach
  public void init() {
    for (int i = 1; i <= 20; i++) {
      String query = "pre_defined_query" + i;
      Trending trending = new Trending(query, 21 - i);
      trendingRepository.save(trending);
      for (int j = i; j <= 20; j++) {
        trendingLogRepository.save(new TrendingLog(query));
      }
    }
  }

  @AfterEach
  public void destroy() {
    trendingRepository.deleteAll();
    trendingLogRepository.deleteAll();
  }

  @DisplayName("TOP10 인기 검색어를 조회한다. -> 성공")
  @Test
  void get_top10_trends_success() throws Exception {
    // Given

    // When & Then
    mockMvc.perform(get("/apis/v1/trends/top10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.rank.size()").value(10))
        .andExpect(jsonPath("$.rank[0].query").value("pre_defined_query" + 1))
        .andExpect(jsonPath("$.rank[0].queryCount").value(20))
    ;
  }

}