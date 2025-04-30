package com.sparta.moim.gathering.gathering.infrastructure.event.listener;

import com.sparta.moim.gathering.gathering.application.exception.MemberLeaveProcessingException;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
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

  @Override
  @Transactional
  public void onMessage(MapRecord<String, String, String> message) {
    try {
      UUID gatheringId = UUID.fromString(message.getValue().get("gathering_id"));
      UUID memberId = UUID.fromString(message.getValue().get("member_id"));
      log.info("멤버 퇴장 이벤트 수신: gatheringId={}, memberId={}", gatheringId, memberId);
      memberRepository.deleteByGatheringIdAndMemberId(gatheringId, memberId);
    } catch (Exception e) {
      log.error("멤버 퇴장 처리 중 오류 발생: {}", e.getMessage(), e);
      throw new MemberLeaveProcessingException();
    }
  }
}
