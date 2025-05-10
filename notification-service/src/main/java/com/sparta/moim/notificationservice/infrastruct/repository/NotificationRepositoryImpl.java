package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;
    private final JdbcTemplate jdbcTemplate;

//    @Override
//    public void saveAll(List<Notification> notificationList){
//        notificationJpaRepository.saveAll(notificationList);
//    }
    @Override
    public void saveAll(List<Notification> notificationList) {
        String sql = "INSERT INTO p_notification_partition ("
                + "tracking_id, notification_type, access_tracking_id, receiver_tracking_id, content, is_read, "
                + "created_at, created_by, modified_at, modified_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Notification notification = notificationList.get(i);
                        UUID trackingId = UUID.randomUUID();
                        LocalDateTime createdAt = LocalDateTime.now();
                        LocalDateTime modifiedAt = LocalDateTime.now();

                        ps.setString(1, trackingId.toString());
                        ps.setString(2, notification.getNotificationType().name());
                        ps.setString(3, notification.getAccessTrackingId().toString());
                        ps.setString(4, notification.getReceiverTrackingId().toString());
                        ps.setString(5, notification.getContent());
                        ps.setBoolean(6, false); // 읽지 않음
                        ps.setTimestamp(7, Timestamp.valueOf(createdAt));
                        ps.setString(8, "System"); // 생성자
                        ps.setTimestamp(9, Timestamp.valueOf(modifiedAt));
                        ps.setString(10, "System"); // 수정자
                    }

                    @Override
                    public int getBatchSize() {
                        return notificationList.size();
                    }
            }
        );
    }

    @Override
    public Pagination<Notification> findByUserTrackingIdAndIsRead(
            String userTrackingId,
            Boolean isRead,
            LocalDateTime startDate,
            int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage = notificationJpaRepository.findByUserTrackingIdAndIsRead(UUID.fromString(userTrackingId), isRead, startDate, pageable);
        return Pagination.of(
                notificationPage.getNumber(),
                notificationPage.getSize(),
                notificationPage.getTotalElements(),
                notificationPage.getContent()
        );
    }

    @Override
    public Optional<Notification> findByUserTrackingIdAndNotificationTrackingId(String userTrackingId,
                                                                                String notificationTrackingId) {
        return notificationJpaRepository.findByReceiverTrackingIdAndTrackingId(UUID.fromString(userTrackingId), UUID.fromString(notificationTrackingId));
    }

    @Override
    public void save(Notification notification) {
        notificationJpaRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(long id) {
        return notificationJpaRepository.findById(id);
    }
}
