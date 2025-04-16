package com.moim.schedule.application;

import com.moim.schedule.application.exception.NotFoundSchedule;
import com.moim.schedule.application.query.FindQuery;
import com.moim.schedule.application.query.SearchScheduleQuery;
import com.moim.schedule.application.usecase.ScheduleQueryUseCase;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.domain.repository.query.ScheduleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleQueryService implements ScheduleQueryUseCase {

  private final ScheduleQueryRepository repository;

  @Override
  public Schedule findSchedule(FindQuery query) {
    return repository.findSchedule(query.id()).orElseThrow(NotFoundSchedule::new);
  }

  @Override
  public Page<Schedule> searchSchedule(
      SearchScheduleQuery query,
      int page,
      int size,
      String sortType
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, sortType));
    return repository.searchSchedule(
        query.organizationId(),
        query.word(),
        query.start(),
        query.end(),
        pageable
    );
  }
}
