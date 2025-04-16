package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;


    @Override
    public void saveAll(List<Notification> notificationList) {
        notificationJpaRepository.saveAll(notificationList);
    }

    @Override
    public Pagination<Notification> findByUserTrackingIdAndIsRead(
            String userTrackingId,
            Boolean isRead,
            int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage = notificationJpaRepository.findByUserTrackingIdAndIsRead(userTrackingId, isRead, pageable);
        return Pagination.of(
                notificationPage.getNumber(),
                notificationPage.getSize(),
                notificationPage.getTotalElements(),
                notificationPage.getContent()
        );
    }
}
