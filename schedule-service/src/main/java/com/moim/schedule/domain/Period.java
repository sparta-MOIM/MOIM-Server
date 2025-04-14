package com.moim.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  public void updateStart(LocalDateTime start) {
    this.start = start;
  }

  public void updateEnd(LocalDateTime end) {
    this.end = end;
  }
}
