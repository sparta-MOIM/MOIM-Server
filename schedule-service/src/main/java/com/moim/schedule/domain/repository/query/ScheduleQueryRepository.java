package com.moim.schedule.domain.repository.query;

import com.moim.schedule.domain.Schedule;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleQueryRepository {
  Optional<Schedule> findSchedule(UUID id);

  Page<Schedule> searchSchedule(
      Optional<UUID> organizationId,
      Optional<String> word,
      Optional<LocalDateTime> start,
      Optional<LocalDateTime> end,
      Pageable pageable
  );
}
