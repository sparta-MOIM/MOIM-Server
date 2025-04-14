package com.moim.schedule.application;

import com.moim.schedule.application.command.CreateScheduleCommand;
import com.moim.schedule.application.usecase.ScheduleCommandUseCase;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.domain.repository.command.ScheduleCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleCommandService implements ScheduleCommandUseCase {

  private final ScheduleCommandRepository repository;

  @Override
  public Schedule createSchedule(CreateScheduleCommand command) {
    Schedule schedule = Schedule.create(
        command.organizationId(),
        command.title(),
        command.content(),
        command.start(),
        command.end()
    );
    return repository.save(schedule);
  }
}
