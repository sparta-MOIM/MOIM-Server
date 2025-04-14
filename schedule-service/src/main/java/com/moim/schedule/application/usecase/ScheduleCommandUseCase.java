package com.moim.schedule.application.usecase;

import com.moim.schedule.application.command.CreateScheduleCommand;
import com.moim.schedule.domain.Schedule;

public interface ScheduleCommandUseCase {
  Schedule createSchedule(CreateScheduleCommand command);
}
