package com.moim.schedule.application.usecase;

import com.moim.schedule.application.command.CreateScheduleCommand;
import com.moim.schedule.application.command.DeleteCommand;
import com.moim.schedule.application.command.UpdateScheduleCommand;
import com.moim.schedule.domain.Schedule;
import java.util.UUID;

public interface ScheduleCommandUseCase {
  Schedule createSchedule(CreateScheduleCommand command);
  void deleteSchedule(DeleteCommand command);
  Schedule updateSchedule(UUID id, UpdateScheduleCommand command);
}
