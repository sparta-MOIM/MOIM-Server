package com.moim.post.domain.vote;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {

  @Column(name = "start", nullable = false)
  private LocalDateTime start;

  @Column(name = "end", nullable = false)
  private LocalDateTime end;

  public Period(LocalDateTime start, LocalDateTime end) {
    this.start = start;
    this.end = end;
  }
}
