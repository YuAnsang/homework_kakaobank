package com.github.asyu.homework.domain.blog.implement.event;

import com.github.asyu.homework.common.event.QueryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QueryEventPublisher {

  private final ApplicationEventPublisher publisher;

  public void publish(String query) {
    publisher.publishEvent(new QueryEvent(query));
  }

}
