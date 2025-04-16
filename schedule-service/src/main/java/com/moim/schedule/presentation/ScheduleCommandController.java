package com.moim.schedule.presentation;

import com.moim.schedule.application.usecase.ScheduleCommandUseCase;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.presentation.mapper.SchedulePresentationMapper;
import com.moim.schedule.presentation.request.CreateScheduleRequest;
import com.moim.schedule.presentation.request.UpdateScheduleRequest;
import com.moim.schedule.presentation.response.ScheduleResponse;
import com.sparta.moim.common.response.ApiResponseData;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<ApiResponseData<ScheduleResponse>> createSchedule(
      @Valid @RequestBody CreateScheduleRequest request
  ) {
    log.info("Schedule 생성 요청: {}", request.toString());
    Schedule schedule = useCase.createSchedule(mapper.toCommand(request));
    ScheduleResponse response = mapper.toResponse(schedule);
    log.info("Schedule 생성 및 저장 완료: {}", response.id().toString());
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }

  @PostMapping("/{id}")
  public ResponseEntity<ApiResponseData<ScheduleResponse>> updateSchedule(
      @PathVariable final String id,
      @RequestBody UpdateScheduleRequest request
  ) {
    log.info("Schedule 업데이트 요청: {}", id);
    log.info("Schedule 업데이트 내역: {}", request.toString());
    Schedule schedule = useCase.updateSchedule(UUID.fromString(id), mapper.toCommand(request));
    ScheduleResponse response = mapper.toResponse(schedule);
    log.info("Schedule 업데이트 완료");
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseData> deleteSchedule(@PathVariable final String id) {
    log.info("schedule 삭제 요청: {}", id);
    useCase.deleteSchedule(mapper.toCommand(UUID.fromString(id)));
    log.info("schedule 삭제 완료");
    return ResponseEntity.ok().body(ApiResponseData.success(null));
  }
}
