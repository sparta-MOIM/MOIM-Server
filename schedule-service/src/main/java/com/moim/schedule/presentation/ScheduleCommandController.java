package com.moim.schedule.presentation;

import com.moim.schedule.application.usecase.ScheduleCommandUseCase;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.presentation.mapper.SchedulePresentationMapper;
import com.moim.schedule.presentation.request.CreateScheduleRequest;
import com.moim.schedule.presentation.response.ScheduleResponse;
import com.sparta.moim.common.response.ApiResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleCommandController {

  private final ScheduleCommandUseCase useCase;
  private final SchedulePresentationMapper mapper;

  @PostMapping
  public ResponseEntity<ApiResponseData<ScheduleResponse>> createFeed(
      @Valid @RequestBody CreateScheduleRequest request
  ) {
    log.info("Schedule 생성 요청: {}", request.toString());
    Schedule schedule = useCase.createSchedule(mapper.toCommand(request));
    ScheduleResponse response = mapper.toResponse(schedule);
    log.info("Schedule 생성 및 저장 완료: {}", response.id().toString());
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }
}
