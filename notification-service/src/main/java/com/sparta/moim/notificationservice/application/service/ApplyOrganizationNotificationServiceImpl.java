package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplyOrganizationNotificationServiceImpl implements ApplyOrganizationNotificationService {

    private final NotificationTemplateUtilService notificationTemplateUtilService;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Override
    public void sendApplyOrganizationNotification(ApplyOrganizationNotificationCommand command) {
        // 템플릿을 가져와서 내용과 결합한다.
        String content = notificationTemplateUtilService.geteNotificationContent(command);

        // 알림을 보낸다.
        notificationService.sendNotificationToMembers(command.getReceiverTrackingIds(), content);
        log.info("알림을 보냈습니다. 내용: {}", content);

        // 알림을 저장한다.
        List<Notification> notificationList = new ArrayList<>();
        for(String receiverTrackingId : command.getReceiverTrackingIds()) {
            Notification notification = Notification.from(command, receiverTrackingId, content);
            notificationList.add(notification);
        }
        notificationRepository.saveAll(notificationList);

    }
}
