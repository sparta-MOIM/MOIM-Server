package com.moim.schedule.presentation.mapper;

import com.moim.schedule.application.command.CreateScheduleCommand;
import com.moim.schedule.application.command.DeleteCommand;
import com.moim.schedule.application.query.FindQuery;
import com.moim.schedule.application.query.SearchScheduleQuery;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.presentation.request.CreateScheduleRequest;
import com.moim.schedule.presentation.request.SearchScheduleRequest;
import com.moim.schedule.presentation.response.ScheduleResponse;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SchedulePresentationMapper {
  CreateScheduleCommand toCommand(CreateScheduleRequest request);

  @Mapping(source = "trackingId", target = "id")
  @Mapping(source = "period.start", target = "start")
  @Mapping(source = "period.end", target = "end")
  ScheduleResponse toResponse(Schedule schedule);

  FindQuery toQuery(UUID id);

  SearchScheduleQuery toQuery(SearchScheduleRequest request);

  DeleteCommand toCommand(UUID id);

}
