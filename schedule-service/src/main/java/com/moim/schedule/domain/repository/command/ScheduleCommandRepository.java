package com.moim.schedule.domain.repository.command;

import com.moim.schedule.domain.Schedule;

public interface ScheduleCommandRepository {
  Schedule save(Schedule schedule);
}
