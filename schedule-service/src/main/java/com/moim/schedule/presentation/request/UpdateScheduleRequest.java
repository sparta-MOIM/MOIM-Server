package com.moim.schedule.presentation.request;

import java.time.LocalDateTime;
import java.util.Optional;

public record UpdateScheduleRequest(
    Optional<String> title,
    Optional<String> content,
    Optional<LocalDateTime> start,
    Optional<LocalDateTime> end
){
  public UpdateScheduleRequest(
      String title,
      String content,
      LocalDateTime start,
      LocalDateTime end
  ){
    this(
        Optional.ofNullable(title),
        Optional.ofNullable(content),
        Optional.ofNullable(start),
        Optional.ofNullable(end)
    );
  }
}

