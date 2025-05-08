package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.notificationservice.domain.entity.Notification;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

    @Query("""
        SELECT n FROM Notification n
        WHERE n.receiverTrackingId = :userTrackingId
          AND (:isRead IS NULL OR n.isRead = :isRead)
          AND (:startDate IS NULL OR n.createdAt >= :startDate)
        ORDER BY n.createdAt DESC
    """)
    Page<Notification> findByUserTrackingIdAndIsRead(
            UUID userTrackingId,
            Boolean isRead,
            LocalDateTime startDate,
            Pageable pageable);

    Optional<Notification> findByReceiverTrackingIdAndTrackingId(UUID userTrackingId, UUID trackingId);
}
