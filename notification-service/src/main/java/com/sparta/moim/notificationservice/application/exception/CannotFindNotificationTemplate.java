package com.sparta.moim.notificationservice.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.notificationservice.presentation.code.NotificationCode;

public class CannotFindNotificationTemplate extends BaseException {
    public CannotFindNotificationTemplate() {
        super(NotificationCode.CANNOT_FIND_NOTIFICATION_TEMPLATE);
    }
}
