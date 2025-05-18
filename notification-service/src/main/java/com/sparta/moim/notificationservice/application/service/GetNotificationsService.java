package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.notificationservice.application.dto.query.GetNotificationQuery;
import com.sparta.moim.notificationservice.application.mapper.ResponseMapper;
import com.sparta.moim.notificationservice.application.usecase.GetNotificationsUseCase;
import com.sparta.moim.notificationservice.application.util.PaginationMap;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import com.sparta.moim.notificationservice.presentation.dto.GetNotificationResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetNotificationsService implements GetNotificationsUseCase {

    private final NotificationRepository notificationRepository;
    private final ResponseMapper responseMapper;
    private final static int NOTIFICATION_DURATION = 14; // 최근 14일 알림만 조회
    @Override
    public Pagination<GetNotificationResponse> execute(String userTrackingId, Boolean isRead, int page, int size) {
        // 2025-05-17 날짜를 LocalDate로 변환. 테스트를 위해 진행 ( todo -추후삭제)
        String dateStr = "2025-05-31";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime startDate = LocalDate.parse(dateStr, formatter)
                .atStartOfDay()
                .minusDays(NOTIFICATION_DURATION);

        Pagination<Notification> notifications = notificationRepository.findByUserTrackingIdAndIsRead(
                userTrackingId, isRead, startDate,page, size);
        Pagination<GetNotificationQuery> getNotificationQueryPagination = PaginationMap.map(notifications, GetNotificationQuery::from);
        return PaginationMap.map(getNotificationQueryPagination, responseMapper::toResponse);
    }
}
