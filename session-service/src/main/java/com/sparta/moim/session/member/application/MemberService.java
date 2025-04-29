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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

  @Transactional
  public void joinMember(JoinMemberCommand command) {
    joinValidate(command.sessionId(), command.username());
    Member member = Member.builder()
        .sessionId(command.sessionId())
        .type(MemberType.GENERAL)
        .memberName(command.username())
        .build();

//    memberRepository.save(member);

    redisTemplate.opsForStream().add(streamJoinKey, member.toMap());

    handleSessionMemberCountPublisher.increase(command.sessionId(), command.username());
  }

  private void joinValidate(UUID sessionId, String memberName) {
    sessionService.getSessionValidate(sessionId);
    sessionService.getSessionValidateTime(sessionId);
    sessionService.getSessionValidateOpenStatus(sessionId);
    isAlreadyParticipation(sessionId, memberName);

  }

  private void isAlreadyParticipation(UUID sessionId, String username) {
    if (memberRepository.existsBySessionIdAndMemberName(sessionId, username)) {
      throw new SessionException(SessionCode.ALREADY_PARTICIPATE_SESSION);
    }
  }

  @Transactional
  public void leaveMember(LeaveMemberCommand command) {
    memberRepository.deleteMemberBySessionId(command.sessionId(), command.username());
    handleSessionMemberCountPublisher.decrease(command.sessionId(), command.username());
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
