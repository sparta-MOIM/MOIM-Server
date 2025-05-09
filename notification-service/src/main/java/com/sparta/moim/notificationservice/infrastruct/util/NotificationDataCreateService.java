package com.sparta.moim.notificationservice.infrastruct.util;

import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationDataCreateService {

    private final NotificationRepository notificationRepository;

    private static final String CONTENT = "test0004이 모임_600에 신청하였습니다.";
    private static final NotificationType TYPE = NotificationType.ORGANIZATION_MOIM_REQUEST;
    private static final UUID ACCESS_TRACKING_ID = UUID.fromString("273154f6-d795-4309-a8a0-4ccd1bc6a64e");
    private static final List<UUID> RECEIVER_IDS = List.of(
            UUID.fromString("a1dbdc43-b296-4029-a3dd-d496344a04e6"),
            UUID.fromString("31c772cb-38a5-4b50-8a10-603f8bd940a4"),
            UUID.fromString("19a80bca-7be6-4a25-b6b0-cb2ef7a5bbfe"),
            UUID.fromString("48c8bd22-b80b-4966-953d-f1e366ca2d08"),
            UUID.fromString("059b1c97-cec7-4c5c-82c9-824b48762856"),
            UUID.fromString("a09cf1e2-6d52-4af2-839e-b9d131c9c055"),
            UUID.fromString("217cc2f3-eaf9-4ab0-aae1-aa734225ff36"),
            UUID.fromString("d5a4fcf3-8ae4-42f4-b4d1-d51c9767d339"),
            UUID.fromString("64ed51f1-90fe-451f-bae9-b9bd2003702e"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f"),
            UUID.fromString("e3495b9e-d253-49ff-9f40-dc843783b61f")

    );

    @Transactional
    public void generateDailyNotifications(int day) {
        List<Notification> notifications = new ArrayList<>();
        LocalDateTime baseDate = LocalDateTime.of(2025, 5, day, 12, 0); // 정오 기준 시간

        for (UUID receiverId : RECEIVER_IDS) {
            for (int i = 0; i < 100; i++) {
                Notification notification = Notification.builder()
                        .trackingId(UUID.randomUUID())
                        .notificationType(TYPE)
                        .accessTrackingId(ACCESS_TRACKING_ID)
                        .receiverTrackingId(receiverId)
                        .content(CONTENT)
                        .isRead(false)
                        .createdAt(baseDate)
                        .modifiedAt(baseDate)
                        .createdBy("System")
                        .modifiedBy("System")
                        .build();
                log.info("알림 생성날짜" +String.valueOf(notification.getCreatedAt()));
                notifications.add(notification);
                notificationRepository.save(notification);
            }
        }

        log.info("✅ {}일 알림 1000건 생성 완료", day);
    }
}
