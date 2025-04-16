package com.sparta.moim.notificationservice.infrastruct.repository;

import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;


    @Override
    public void saveAll(List<Notification> notificationList) {
        notificationJpaRepository.saveAll(notificationList);
    }
}
