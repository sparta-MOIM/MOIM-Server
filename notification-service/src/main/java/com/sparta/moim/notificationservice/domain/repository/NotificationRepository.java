package com.sparta.moim.notificationservice.domain.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    void saveAll(List<Notification> notificationList);

    Pagination<Notification> findByUserTrackingIdAndIsRead(
            String userTrackingId,
            Boolean isRead,
            LocalDateTime startDate,
            int page, int size);

    Optional<Notification> findByUserTrackingIdAndNotificationTrackingId(String userTrackingId, String notificationTrackingId);

    void save(Notification notification);

    Optional<Notification> findById(long id);
}
