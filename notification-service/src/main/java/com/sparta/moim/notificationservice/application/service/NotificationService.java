package com.sparta.moim.notificationservice.application.service;

import com.sparta.moim.notificationservice.domain.repository.EmitterRepository;
import com.sparta.moim.notificationservice.domain.repository.NotificationRepository;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterRepository emitterRepository;

    //연결 지속 시간
    private static final Long DEFAULT_TIMEOUT = 60L * 1000L * 60; // 1시간

    public SseEmitter subscribe(String userTrackingId, String lastEventId){
        // 고유한 아이디 생성
        String emitterId = userTrackingId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 시간 초과나 비동기 요청 실패시 자동으로 삭제
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));

        // 최초 연결시 더미데이터가 없으면 503 오류 발생. 더미 데이터 생성
        sendToClient(emitter, emitterId, "EventStream Created. [memberId=" + userTrackingId + "]");
        // lastEventId가 있다는것은 연결이 종료됐었다는 의미. 이벤트가 남아 있을 경우 클라이언트에게 전송
        if(!lastEventId.isEmpty()){
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithMyMemberId(userTrackingId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey())<0)
                    .forEach(entry -> sendToClient(emitter,entry.getKey(),entry.getValue()));
        }
        log.info("SSE 구독 연결: memberId = " + userTrackingId);
        return emitter;

    }

    private void sendToClient(SseEmitter emitter, String messageId, Object data) {

        try {
            emitter.send(SseEmitter.event()
                    .id(messageId)
                    .data(data));
        } catch (IOException exception){
            log.error("emitter 전송 실패: {}", exception.getMessage());
            emitterRepository.deleteById(messageId);
            throw new RuntimeException("전송 실패");
        }
    }

    public void sendNotificationToMember(String memberTrackingId, String message) {
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(memberTrackingId);
        emitters.forEach((emitterId, emitter) -> {
            // 메시지 ID는 UUID로 생성 → 재전송을 위한 고유 ID
            String messageId = UUID.randomUUID().toString();

            // 재연결 대비 메시지 캐시
            emitterRepository.saveEventCache(messageId, message);

            log.info("받는 사람 : " + memberTrackingId + ", 메시지 ID : " + messageId + ", 메시지 : " + message);
            // 실제 전송
            sendToClient(emitter, messageId, message);
        });
    }

    public void sendNotificationToMembers(List<String> memberTrackingIds, String message) {
        for (String memberTrackingId : memberTrackingIds) {
            sendNotificationToMember(memberTrackingId, message);
        }
    }
}
