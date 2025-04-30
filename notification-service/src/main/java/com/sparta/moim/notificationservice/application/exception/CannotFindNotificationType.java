package com.sparta.moim.notificationservice.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.notificationservice.presentation.code.NotificationCode;

public class CannotFindNotificationType extends BaseException {
    public CannotFindNotificationType() {
        super(NotificationCode.CANNOT_FIND_NOTIFICATION_TYPE);
    }
}
