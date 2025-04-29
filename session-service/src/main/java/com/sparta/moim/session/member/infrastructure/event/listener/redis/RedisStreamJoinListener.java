package com.sparta.moim.session.member.infrastructure.event.listener.redis;


import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.enums.MemberType;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisStreamJoinListener implements StreamListener<String, MapRecord<String, String, String>> {
  private final MemberRepository memberRepository;

  @Override
  public void onMessage(MapRecord<String, String, String> message) {
    UUID sessionId = UUID.fromString(message.getValue().get("session_id"));
//    Member owner = memberRepository.existsOwner(sessionId).orElse(null);
//    // 리더가 존재하지 않는 경우 무시
//    if (owner == null) {
//      return;
//    }

    memberRepository.save(Member.builder()
        .sessionId(sessionId)
        .memberId(UUID.fromString(message.getValue().get("member_id")))
        .type(MemberType.valueOf(message.getValue().get("type")))
        .build());
  }
}
