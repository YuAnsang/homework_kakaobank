package com.github.asyu.homework.domain.trending.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Trending {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String query;

  private Integer queryCount;

  public Trending(String query, Integer queryCount) {
    this.query = query;
    this.queryCount = queryCount;
  }

  public void increaseQueryCount() {
    this.queryCount = this.queryCount + 1;
  }

}
