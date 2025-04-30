package com.sparta.moim.notificationservice.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import com.sparta.moim.notificationservice.domain.entity.Notification;
import com.sparta.moim.notificationservice.domain.enums.NotificationType;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplyOrganizationNotificationHandlerStrategy implements NotificationHandlerStrategy {

    private final NotificationTemplateUtilService notificationTemplateUtilService;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final ObjectMapper objectMapper;

    @Override
    public NotificationType getType() {
        return NotificationType.ORGANIZATION_MOIM_REQUEST;
    }

    @Override
    public void handleNotification(String rawJson) {
        try{
            ApplyOrganizationNotificationCommand command = objectMapper.readValue(rawJson, ApplyOrganizationNotificationCommand.class);
            // 템플릿을 가져와서 내용과 결합한다.
            String content = notificationTemplateUtilService.getNotificationContent(command);

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
        } catch (JsonProcessingException e){
            log.error("JSON 파싱 오류: {}", e.getMessage());
        } catch (Exception e) {
            log.error("알림 처리 중 오류 발생: {}", e.getMessage());
        }

    }
}
