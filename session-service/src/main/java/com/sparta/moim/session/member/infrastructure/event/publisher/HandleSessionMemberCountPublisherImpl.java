package com.sparta.moim.session.member.infrastructure.event.publisher;

import com.sparta.moim.session.member.application.event.publisher.HandleSessionMemberCountPublisher;
import com.sparta.moim.session.shared.dto.SharedDecreaseMember;
import com.sparta.moim.session.shared.dto.SharedIncreaseMember;
import com.sparta.moim.session.shared.dto.SharedRemoveMember;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 세션의 멤버 수를 관리하기 위한 이벤트 발행 인터페이스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleSessionMemberCountPublisherImpl implements HandleSessionMemberCountPublisher {
  private final ApplicationEventPublisher publisher;

  /**
   * 새 멤버가 세션에 참여할 때 현재 인원 수를 증가시킵니다.
   *
   * @param sessionId 세션 ID
   * @param memberId  멤버 ID
   */
  @Async
  public void increase(UUID sessionId, UUID memberId) {
    try {
      publisher.publishEvent(new SharedIncreaseMember(sessionId, memberId));
    } catch (Exception ex) {
      log.error("세션 멤버 증가 이벤트 발행 중 예외 발생: sessionId={}, memberId={}", sessionId, memberId, ex);
    }
  }

  /**
   * 멤버가 세션을 떠날 때 현재 인원 수를 감소시킵니다.
   *
   * @param sessionId 세션 ID
   * @param memberId  멤버 ID
   */
  @Async
  public void decrease(UUID sessionId, UUID memberId) {
    try {
      publisher.publishEvent(new SharedDecreaseMember(sessionId, memberId));
    } catch (Exception ex) {
      log.error("세션 멤버 감소 이벤트 발행 중 예외 발생: sessionId={}, memberId={}", sessionId, memberId, ex);
    }
  }

  /**
   * 여러 멤버가 한 번에 제거될 때 현재 인원 수를 조정합니다.
   *
   * @param sessionId 세션 ID
   * @param count     제거할 멤버 수
   */
  public void remove(UUID sessionId, long count) {
    publisher.publishEvent(new SharedRemoveMember(sessionId, count));
  }

}
