package com.sparta.moim.session.member.application;

import com.sparta.moim.session.member.application.dto.command.GetMemberCommand;
import com.sparta.moim.session.member.application.dto.command.JoinMemberCommand;
import com.sparta.moim.session.member.application.dto.command.LeaveMemberCommand;
import com.sparta.moim.session.member.application.dto.command.RemoveMemberCommand;
import com.sparta.moim.session.member.application.dto.result.GetMemberListResult;
import com.sparta.moim.session.member.application.event.feign.SessionInternalService;
import com.sparta.moim.session.member.application.event.publisher.HandleSessionMemberCountPublisher;
import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.enums.MemberType;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import com.sparta.moim.session.shared.error.code.SessionCode;
import com.sparta.moim.session.shared.error.exception.SessionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final SessionInternalService sessionService;
  private final HandleSessionMemberCountPublisher handleSessionMemberCountPublisher;


  @Value("${spring.data.redis.stream-join-key}")
  private String streamJoinKey;

  @Value("${spring.data.redis.stream-leave-key}")
  private String streamLeaveKey;

  private final RedisTemplate<String, Member> redisTemplate;

  private final RedissonClient redissonClient;

  @Transactional
  public void joinMember(JoinMemberCommand command) {
    UUID sessionId = command.sessionId();
    UUID userId = command.userId();
    joinValidate(sessionId, userId);

    String lockKey = "join:" + sessionId + ":" + userId;
    RLock lock = redissonClient.getLock(lockKey);

    try {
      // 락 획득 시도 (10초 대기, 30초 유지)
      boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);
      if (isLocked) {
        try {
          joinValidate(command.sessionId(), command.userId());
          Member member = Member.builder()
              .sessionId(command.sessionId())
              .type(MemberType.GENERAL)
              .memberId(command.userId())
              .build();

          redisTemplate.opsForStream().add(streamJoinKey, member.toMap());
          handleSessionMemberCountPublisher.increase(command.sessionId(), command.userId());
        } finally {
          // 락 해제
          lock.unlock();
        }
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("락 획득 중 인터럽트 발생", e);
    }

  }

  private void joinValidate(UUID sessionId, UUID memberId) {
    sessionService.getSessionValidate(sessionId);
    sessionService.getSessionValidateTime(sessionId);
    sessionService.getSessionValidateOpenStatus(sessionId);
    isAlreadyParticipation(sessionId, memberId);

  }

  private void isAlreadyParticipation(UUID sessionId, UUID userId) {
    if (memberRepository.existsBySessionIdAndMemberId(sessionId, userId)) {
      throw new SessionException(SessionCode.ALREADY_PARTICIPATE_SESSION);
    }
  }

  @Transactional
  public void leaveMember(LeaveMemberCommand command) {
//    memberRepository.deleteMemberBySessionId(command.sessionId(), command.username());

    UUID sessionId = command.sessionId();
    UUID userId = command.userId();

    String lockKey = "leave:" + sessionId + ":" + userId;
    RLock lock = redissonClient.getLock(lockKey);
    // 락 획득 시도 (10초 대기, 30초 유지)
    try {
      boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);

      try {
        if (isLocked) {
          Map<String, String> map = new HashMap<>();
          map.put("session_id", command.sessionId().toString());
          map.put("member_id", command.userId().toString());
          redisTemplate.opsForStream().add(streamLeaveKey, map);
          handleSessionMemberCountPublisher.decrease(command.sessionId(), command.userId());
        }

      } finally {
        // 락 해제
        lock.unlock();
      }

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("락 획득 중 인터럽트 발생", e);
    }

  }

  @Transactional
  public void removeMember(RemoveMemberCommand command) {
    long count = memberRepository.countMembersUnpublishable(command.sessionId(), command.members());
    handleSessionMemberCountPublisher.remove(command.sessionId(), count);
    memberRepository.removeMembers(command.sessionId(), command.members());
  }

  @Transactional(readOnly = true)
  public List<GetMemberListResult> getMember(GetMemberCommand command) {
    sessionService.getSessionValidate(command.sessionId());
    return getMemberRepositoryAllBySessionId(command.sessionId()).stream().map(GetMemberListResult::new)
        .collect(Collectors.toList());
  }

  private List<Member> getMemberRepositoryAllBySessionId(UUID sessionId) {
    return memberRepository.findAllBySessionId(sessionId);
  }
}
