package com.sparta.moim.notificationservice.domain.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import java.util.List;

public interface NotificationRepository {
    void saveAll(List<Notification> notificationList);

    Pagination<Notification> findByUserTrackingIdAndIsRead(String userTrackingId, Boolean isRead, int page, int size);
}
