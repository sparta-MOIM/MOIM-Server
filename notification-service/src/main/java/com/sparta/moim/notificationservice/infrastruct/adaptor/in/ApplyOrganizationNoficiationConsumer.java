package com.sparta.moim.notificationservice.infrastruct.adaptor.in;

import com.sparta.moim.notificationservice.infrastruct.dto.message.ApplyOrganizationNotificationMessage;

public interface ApplyOrganizationNoficiationConsumer {
    void consume(ApplyOrganizationNotificationMessage message);
}
