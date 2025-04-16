package com.sparta.moim.notificationservice.application.usecase;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.notificationservice.presentation.dto.NotificationResponse;
import java.util.List;

public interface GetNotificationsUseCase {
    Pagination<NotificationResponse> execute(String userTrackingId, Boolean isRead, int page, int size);
}
