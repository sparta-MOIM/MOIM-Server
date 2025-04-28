package com.sparta.moim.notificationservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    ORGANIZATION_MOIM_REQUEST("모임 초대 요청"),
    ORGANIZATION_MOIM_ACCEPT("모임 초대 수락"),
    ORGANIZATION_MOIM_REJECT("모임 초대 거절"),
    CHAT_MESSAGE("채팅 메시지"),
    SESSION_OPEN("세션 오픈"),
    SESSION_CLOSE("세션 종료"),
    UNREAD_USERS("게시물 확인 요청"),
    ;

    private final String description;
}
