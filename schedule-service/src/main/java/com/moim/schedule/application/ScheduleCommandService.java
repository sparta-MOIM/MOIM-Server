package com.moim.schedule.application;

import com.moim.schedule.application.command.CreateScheduleCommand;
import com.moim.schedule.application.command.DeleteCommand;
import com.moim.schedule.application.command.UpdateScheduleCommand;
import com.moim.schedule.application.usecase.ScheduleCommandUseCase;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.domain.repository.command.ScheduleCommandRepository;
import java.util.UUID;
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

  @Override
  public Schedule updateSchedule(UUID id, UpdateScheduleCommand command) {
    // todo : 예외처리
    Schedule schedule = repository.findByTrackingId(id).orElseThrow(null);
    command.title().ifPresent(schedule::updateTitle);
    command.content().ifPresent(schedule::updateContent);
    command.start().ifPresent(schedule::updateStart);
    command.end().ifPresent(schedule::updateEnd);
    return schedule;
  }

  @Override
  public void deleteSchedule(DeleteCommand command) {
    // todo: 예외처리
    Schedule schedule = repository.findByTrackingId(command.id()).orElseThrow(null);
    schedule.delete();
  }
}
