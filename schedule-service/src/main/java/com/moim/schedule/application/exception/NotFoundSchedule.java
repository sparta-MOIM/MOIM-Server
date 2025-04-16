package com.moim.schedule.application.exception;

import com.moim.schedule.application.exception.code.ScheduleExceptionCode;
import com.sparta.moim.common.exception.BaseException;

public class NotFoundSchedule extends BaseException {
    public NotFoundSchedule() {
        super(ScheduleExceptionCode.NOT_FOUND_SCHEDULE);
    }
}
