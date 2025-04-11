package com.moim.schedule.presentation.mapper;

import com.moim.schedule.application.command.CreateScheduleCommand;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.presentation.request.CreateScheduleRequest;
import com.moim.schedule.presentation.response.ScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SchedulePresentationMapper {
  CreateScheduleCommand toCommand(CreateScheduleRequest request);

  @Mapping(source = "trackingId", target = "id")
  @Mapping(source = "period.start", target = "start")
  @Mapping(source = "period.end", target = "end")
  ScheduleResponse toResponse(Schedule schedule);

}
