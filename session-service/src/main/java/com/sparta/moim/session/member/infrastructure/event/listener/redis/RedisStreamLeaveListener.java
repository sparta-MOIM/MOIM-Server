package com.sparta.moim.session.member.infrastructure.event.listener.redis;

import com.sparta.moim.session.member.application.event.publisher.HandleSessionMemberCountPublisher;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisStreamLeaveListener implements StreamListener<String, MapRecord<String, String, String>> {
  private final MemberRepository memberRepository;
  private final HandleSessionMemberCountPublisher handleSessionMemberCountPublisher;

  @Override
  @Transactional
  public void onMessage(MapRecord<String, String, String> message) {
    try {
      UUID sessionId = UUID.fromString(message.getValue().get("session_id"));
      UUID memberId = UUID.fromString(message.getValue().get("member_id"));
      handleSessionMemberCountPublisher.decrease(memberId, sessionId);
      log.info("멤버 퇴장 이벤트 수신: gatheringId={}, memberId={}", sessionId, memberId);
      memberRepository.deleteMemberBySessionId(sessionId, memberId);
    } catch (Exception e) {
      log.error("멤버 퇴장 처리 중 오류 발생: {}", e.getMessage(), e);
//      throw new MemberLeaveProcessingException();
    }
  }
}
