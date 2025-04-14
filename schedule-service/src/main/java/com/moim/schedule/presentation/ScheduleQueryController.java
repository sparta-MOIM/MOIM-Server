package com.moim.schedule.presentation;

import com.moim.schedule.application.usecase.ScheduleQueryUseCase;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.presentation.mapper.SchedulePresentationMapper;
import com.moim.schedule.presentation.request.SearchScheduleRequest;
import com.moim.schedule.presentation.response.ScheduleResponse;
import com.sparta.moim.common.response.ApiResponseData;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleQueryController {

  private final ScheduleQueryUseCase useCase;
  private final SchedulePresentationMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponseData<ScheduleResponse>> findVote(@PathVariable final String id) {
    log.info("Schedule 단일 조회 요청: {}", id);
    Schedule schedule = useCase.findSchedule(mapper.toQuery(UUID.fromString(id)));
    ScheduleResponse response = mapper.toResponse(schedule);
    log.info("Schedule 단일 조회 완료");
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }

  @GetMapping
  public ResponseEntity<ApiResponseData<Page<ScheduleResponse>>> searchVotes(
      @ModelAttribute final SearchScheduleRequest request,
      @RequestParam(defaultValue = "0") final int page,
      @RequestParam(defaultValue = "10") final int size,
      @RequestParam(defaultValue = "createdAt") final String sortType
  ) {
    log.info("Schedule 조회 요청: {}", request.toString());
    Page<ScheduleResponse> response = useCase.searchSchedule(mapper.toQuery(request), page, size, sortType)
        .map(mapper::toResponse);
    log.info("Schedule 조회 완료");
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }
}
