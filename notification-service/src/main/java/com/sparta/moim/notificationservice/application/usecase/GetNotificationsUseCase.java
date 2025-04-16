package com.sparta.moim.notificationservice.application.usecase;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.notificationservice.presentation.dto.GetNotificationResponse;

public interface GetNotificationsUseCase {
    Pagination<GetNotificationResponse> execute(String userTrackingId, Boolean isRead, int page, int size);
}
