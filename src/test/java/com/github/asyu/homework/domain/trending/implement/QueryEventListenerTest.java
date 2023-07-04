package com.github.asyu.homework.domain.trending.implement;

import static com.github.asyu.homework.common.SpringProfiles.TEST;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.asyu.homework.domain.blog.implement.event.QueryEventPublisher;
import com.github.asyu.homework.domain.trending.persistence.entity.Trending;
import com.github.asyu.homework.domain.trending.persistence.repository.TrendingRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(TEST)
@SpringBootTest
class QueryEventListenerTest {

  @Autowired
  private QueryEventPublisher eventPublisher;

  @Autowired
  private TrendingRepository trendingRepository;

  @AfterEach
  public void destroy() {
    trendingRepository.deleteAll();
  }

  @DisplayName("한번도 검색된적 없는 검색어에 대해서 저장한다 -> 성공")
  @Test
  void subscribe_query_event_not_exists_entity() {
    // Given
    String query = "not_exists";

    // When
    eventPublisher.publish(query);
    Trending trending = trendingRepository.findByQuery(query).orElseThrow();

    // Then
    assertThat(trending.getId()).isNotNull();
    assertThat(trending.getQuery()).isEqualTo(query);
    assertThat(trending.getQueryCount()).isEqualTo(1);
  }

  @DisplayName("검색된적 있는 검색어에 대해서 저장한다 -> 성공")
  @Test
  void subscribe_query_event_already_exists_entity() {
    // Given
    String query = "already_exists";
    int queryCount = 10;
    Trending existsTrending = new Trending(query, queryCount);
    trendingRepository.save(existsTrending);

    // When
    eventPublisher.publish(query);
    Trending trending = trendingRepository.findByQuery(query).orElseThrow();

    // Then
    assertThat(trending.getId()).isNotNull();
    assertThat(trending.getQuery()).isEqualTo(query);
    assertThat(trending.getQueryCount()).isEqualTo(queryCount + 1);
  }

  @DisplayName("검색된 검색어 저장한다(동시성 이슈를 확인하기 위해서 멀티스레드 환경) -> 성공")
  @Test
  void subscribe_query_event_with_multi_thread() throws InterruptedException {
    // Given
    int numberOfThreads = 100;
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);
    String query = "검색어";

    // When
    for (int i = 0; i < numberOfThreads; i++) {
      executorService.execute(() -> {
        while (latch.getCount() != 0) {
          try {
            eventPublisher.publish(query);
          } catch (Exception e) {
            throw new RuntimeException("Must not occurred!!!", e);
          } finally {
            latch.countDown();
          }
        }
      });
    }
    latch.await();
    Trending trending = trendingRepository.findByQuery(query).orElseThrow();

    // Then
    assertThat(trending.getId()).isNotNull();
    assertThat(trending.getQuery()).isEqualTo(query);
    assertThat(trending.getQueryCount()).isEqualTo(numberOfThreads);
  }
}
