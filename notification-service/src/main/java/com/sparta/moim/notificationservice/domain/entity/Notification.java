package com.sparta.moim.notificationservice.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import com.sparta.moim.notificationservice.application.dto.command.UnReadPostNotificationCommand;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "p_notification")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
    private UUID trackingId;

    @Column(name="notification_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name="access_tracking_id", length = 36, nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID accessTrackingId;

    @Column(name="receiver_tracking_id", length = 36, nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID receiverTrackingId;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="is_read", nullable = false)
    private Boolean isRead;


    public static Notification from(ApplyOrganizationNotificationCommand command, String receiverTrackingId, String content) {
        return Notification.builder()
                .notificationType(NotificationType.ORGANIZATION_MOIM_REQUEST)
                .accessTrackingId(UUID.fromString(command.getOrganizationTrackingId()))
                .receiverTrackingId(UUID.fromString(receiverTrackingId))
                .content(content)
                .isRead(false)
                .build();
    }

    public static Notification from(UnReadPostNotificationCommand command, String receiverTrackingId, String content) {
        return Notification.builder()
                .notificationType(NotificationType.UNREAD_USERS)
                .accessTrackingId(UUID.fromString(command.getPostTrackingId()))
                .receiverTrackingId(UUID.fromString(receiverTrackingId))
                .content(content)
                .isRead(false)
                .build();
    }

    public void updateIsRead(boolean isRead) {
        this.isRead = isRead;
    }


}
