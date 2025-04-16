package com.sparta.moim.notificationservice.presentation.controller;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.notificationservice.application.service.NotificationService;
import com.sparta.moim.notificationservice.application.usecase.GetNotificationsUseCase;
import com.sparta.moim.notificationservice.application.usecase.ReadNotificationUseCase;
import com.sparta.moim.notificationservice.presentation.dto.GetNotificationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final GetNotificationsUseCase getNotificationsUseCase;
    private final ReadNotificationUseCase readNotificationUseCase;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestHeader(value="Last-Event-ID", required = false, defaultValue = "") String lastEventId){
        return notificationService.subscribe("68926367-c01f-4f88-8f10-4c9797b77f8e", lastEventId); // todo - userTrackingId를 실제 값으로 변경
    }

    @GetMapping
    public ResponseEntity<ApiResponseData<Pagination<GetNotificationResponse>>> getNotifications(
//            @AuthenticationPrincipal CustomUserDetails userDetails todo- userDetails로 변경
            @RequestParam(value = "isRead", required = false) Boolean isRead,
            @RequestParam(defaultValue = "0") int page, //todo - @Positive(message = "페이지 번호 1 이상이어야 합니다.")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pagination<GetNotificationResponse> response = getNotificationsUseCase.execute(
                "68926367-c01f-4f88-8f10-4c9797b77f8e", isRead, page, size);
        return ResponseEntity.ok(ApiResponseData.success(response));
    }

    @PatchMapping("/{notificationTrackingId}/read")
    public ResponseEntity<ApiResponseData<String>> readNotification(
            @PathVariable(name = "notificationTrackingId") String notificationTrackingId) { // todo - userTrackingId를 실제 값으로 변경
        readNotificationUseCase.execute("68926367-c01f-4f88-8f10-4c9797b77f8e", notificationTrackingId); // todo - userTrackingId를 실제 값으로 변경
        return ResponseEntity.ok(ApiResponseData.success(null));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<ApiResponseData<String>> test() {
        notificationService.sendNotificationToMember("68926367-c01f-4f88-8f10-4c9797b77f8e", "테스트 알림");
        return ResponseEntity.ok(ApiResponseData.success("test"));
    }


}
