package com.sparta.moim.session.session.infrastructure.event.listener.redis;


import com.sparta.moim.session.session.application.event.publisher.HandleSessionMemberCountPublisher;
import com.sparta.moim.session.session.domain.entity.Member;
import com.sparta.moim.session.session.domain.enums.MemberType;
import com.sparta.moim.session.session.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RedisStreamJoinListener implements StreamListener<String, MapRecord<String, String, String>> {
  private final MemberRepository memberRepository;
  private final HandleSessionMemberCountPublisher handleSessionMemberCountPublisher;

  @Override
  @Transactional
  public void onMessage(MapRecord<String, String, String> message) {
    UUID sessionId = UUID.fromString(message.getValue().get("session_id"));
    UUID memberId = UUID.fromString(message.getValue().get("member_id"));
//    Member owner = memberRepository.existsOwner(sessionId).orElse(null);
//    // 리더가 존재하지 않는 경우 무시
//    if (owner == null) {
//      return;
//    }

    memberRepository.save(Member.builder()
        .sessionId(sessionId)
        .memberId(memberId)
        .type(MemberType.valueOf(message.getValue().get("type")))
        .build());

    handleSessionMemberCountPublisher.increase(sessionId, memberId);
  }
}
