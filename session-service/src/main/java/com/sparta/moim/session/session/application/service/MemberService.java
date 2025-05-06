package com.sparta.moim.session.session.application.service;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.CommonCode;
import com.sparta.moim.session.session.application.dto.command.GetMemberCommand;
import com.sparta.moim.session.session.application.dto.command.JoinMemberCommand;
import com.sparta.moim.session.session.application.dto.command.LeaveMemberCommand;
import com.sparta.moim.session.session.application.dto.command.RemoveMemberCommand;
import com.sparta.moim.session.session.application.dto.context.SessionRedisExecutionContext;
import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;
import com.sparta.moim.session.session.application.dto.result.GetMemberListResult;
import com.sparta.moim.session.session.application.event.publisher.HandleSessionMemberCountPublisher;
import com.sparta.moim.session.session.application.lock.redisson.SessionLock;
import com.sparta.moim.session.session.domain.entity.Member;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.MemberRepository;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import com.sparta.moim.session.shared.enums.MemberType;
import com.sparta.moim.session.shared.enums.OrganizationMemberRole;
import com.sparta.moim.session.shared.error.code.SessionCode;
import com.sparta.moim.session.shared.error.exception.SessionException;
import com.sparta.moim.session.shared.feign.OrganizationService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final SessionValidationService sessionValidationService;
  private final HandleSessionMemberCountPublisher handleSessionMemberCountPublisher;
  private final OrganizationService organizationMemberService;
  private final SessionRepository sessionRepository;

  @Value("${spring.data.redis.stream-join-key}")
  private String streamJoinKey;
  @Value("${spring.data.redis.stream-leave-key}")
  private String streamLeaveKey;

  private final SessionLock sessionLock;

  public void joinMember(JoinMemberCommand command) {
    UUID sessionId = command.sessionId();
    UUID userId = command.userId();
//    checkOtherOrganization(command.sessionId(), command.userId());
    joinValidate(sessionId, userId);
    String lockKey = "join:" + sessionId + ":" + userId;
    sessionLock.access(SessionRedisExecutionContext.builder()
        .event(SendSessionEventMap.builder()
            .sessionId(sessionId.toString())
            .memberId(userId.toString())
            .type(MemberType.GENERAL)
            .build())
        .lockKey(lockKey)
        .streamKey(streamJoinKey)
        .scriptName("sessionJoin")
        .build());

  }

  private void checkOtherOrganization(UUID SessionId, UUID userId) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(SessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    UUID organizationId = UUID.fromString(session.getOrganizationId());

    List<OrganizationMemberRole> roles = List.of(OrganizationMemberRole.MEMBER, OrganizationMemberRole.MASTER,
        OrganizationMemberRole.MANAGER);

    ApiResponseData<Boolean> check = organizationMemberService.checkRole(organizationId, userId, roles);

    if (!Objects.equals(check.getCode(), CommonCode.SUCCESS.getCode())) {
      throw new SessionException(SessionCode.NOT_CONNECTED_SESSION);
    }

    //다른 모임에서 세션을 가입할 수 없다.
    if (!check.getData()) {
      throw new SessionException(SessionCode.ROLE_NOT_ALLOWED_SESSION);
    }
  }

  private void joinValidate(UUID sessionId, UUID memberId) {
    sessionValidationService.isValidateSession(sessionId);
    sessionValidationService.isValidateSessionTimeCheck(sessionId);
    sessionValidationService.isValidateSessionStatus(sessionId);
    isAlreadyParticipation(sessionId, memberId);

  }

  private void isAlreadyParticipation(UUID sessionId, UUID userId) {
    if (memberRepository.existsBySessionIdAndMemberId(sessionId, userId)) {
      throw new SessionException(SessionCode.ALREADY_PARTICIPATE_SESSION);
    }
  }

  @Transactional
  public void leaveMember(LeaveMemberCommand command) {
    UUID sessionId = command.sessionId();
    UUID userId = command.userId();

    String lockKey = "leave:" + sessionId + ":" + userId;
    sessionLock.access(SessionRedisExecutionContext.builder()
        .event(SendSessionEventMap.builder()
            .sessionId(sessionId.toString())
            .memberId(userId.toString())
            .type(MemberType.GENERAL)
            .build())
        .streamKey(streamLeaveKey)
        .lockKey(lockKey)
        .scriptName("sessionLeave")
        .build());

  }

  @Transactional
  public void removeMember(RemoveMemberCommand command) {
    long count = memberRepository.countMembersUnpublishable(command.sessionId(), command.members());
    handleSessionMemberCountPublisher.remove(command.sessionId(), count);
    memberRepository.removeMembers(command.sessionId(), command.members());
  }

  @Transactional(readOnly = true)
  public List<GetMemberListResult> getMember(GetMemberCommand command) {
    sessionValidationService.isValidateSession(command.sessionId());
    return getMemberRepositoryAllBySessionId(command.sessionId()).stream().map(GetMemberListResult::new)
        .collect(Collectors.toList());
  }

  private List<Member> getMemberRepositoryAllBySessionId(UUID sessionId) {
    return memberRepository.findAllBySessionId(sessionId);
  }
}
