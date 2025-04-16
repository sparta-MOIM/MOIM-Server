package com.moim.schedule.domain.repository.command;

import com.moim.schedule.domain.Schedule;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleCommandRepository {
  Schedule save(Schedule schedule);
  Optional<Schedule> findByTrackingId(UUID id);
}
