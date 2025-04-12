package com.moim.schedule.application.usecase;

import com.moim.schedule.application.query.FindQuery;
import com.moim.schedule.application.query.SearchScheduleQuery;
import com.moim.schedule.domain.Schedule;
import org.springframework.data.domain.Page;

public interface ScheduleQueryUseCase {
  Schedule findSchedule(FindQuery query);

  Page<Schedule> searchSchedule(
      SearchScheduleQuery query,
      int page,
      int size,
      String sortTypesortType
  );
}
