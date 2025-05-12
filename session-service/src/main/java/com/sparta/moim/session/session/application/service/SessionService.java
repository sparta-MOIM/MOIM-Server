package com.sparta.moim.session.session.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.CommonCode;
import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.DeleteSessionCommand;
import com.sparta.moim.session.session.application.dto.command.GetMemberCommand;
import com.sparta.moim.session.session.application.dto.command.SearchSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateStateStateCommand;
import com.sparta.moim.session.session.application.dto.result.CreateManagerResult;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.dto.result.GetMemberListResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import com.sparta.moim.session.session.application.dto.result.SearchSessionResult;
import com.sparta.moim.session.session.application.event.publisher.AddMemberPublisher;
import com.sparta.moim.session.session.application.event.publisher.RemoveMemberPublisher;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.SessionCustomRepository;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import com.sparta.moim.session.session.domain.repository.redis.SessionSeatRepository;
import com.sparta.moim.session.shared.dto.SharedRemoveSession;
import com.sparta.moim.session.shared.enums.OrganizationMemberRole;
import com.sparta.moim.session.shared.enums.SessionStatus;
import com.sparta.moim.session.shared.error.code.SessionCode;
import com.sparta.moim.session.shared.error.exception.SessionException;
import com.sparta.moim.session.shared.feign.OrganizationService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;
  private final SessionCustomRepository sessionCustomRepository;
  private final AddMemberPublisher addMemberPublisher;
  private final RemoveMemberPublisher removeMemberPublisher;
  private final MemberService memberService;
  private final OrganizationService organizationSessionService;

  private final SessionSeatRepository sessionSeatRepository;

  @Transactional
  public CreateSessionResult createSession(CreateSessionCommand command) {
    if (sessionRepository.existsByTitleAndDeletedByIsNull(command.title())) {
      throw new SessionException(SessionCode.EXITS_TITLE_SESSION);
    }

    CreateManagerResult createManager = isCreateManager(command.userId(), command);
    Session createSession = sessionRepository.save(command.toDomain(createManager.reason(), createManager.status()));

    createSession.timeValidate();
    addMemberPublisher.add(createSession.getTrackingId(), command.publisher());
    return CreateSessionResult.create(createSession);
  }

  private CreateManagerResult isCreateManager(UUID userId, CreateSessionCommand command) {
    try {
      ApiResponseData<Boolean> check = organizationSessionService.checkRole(UUID.fromString(command.organizationId()),
          userId,
          List.of(OrganizationMemberRole.MASTER,
              OrganizationMemberRole.MANAGER));

      // 200이 발생하지 않는 다면 에러를 리턴한다.
      if (!Objects.equals(check.getCode(), CommonCode.SUCCESS.getCode())) {
        throw new SessionException(SessionCode.NOT_CONNECTED_SESSION);
      }

      // 매니저 이상이 생성한 경우
      if (check.getData()) {
        return new CreateManagerResult("매니저가 생성한 세션입니다.", SessionStatus.OPEN);
      }
    } catch (Exception e) {
      return new CreateManagerResult(null, null);
    }
    return new CreateManagerResult(null, null);
  }

  @Transactional(readOnly = true)
  public GetSessionResult getSession(UUID sessionId) {
    List<GetMemberListResult> members = memberService.getMember(new GetMemberCommand(sessionId));
    return GetSessionResult.get(sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION)), members);
  }

  @Transactional(readOnly = true)
  public SearchSessionResult searchSession(SearchSessionCommand command) {
    Pagination<Session> sessions = sessionCustomRepository.searchSession(command.toCriteria());
    return SearchSessionResult.search(sessions.getContent(),
        sessions.getTotal(),
        sessions.getPage(),
        sessions.getSize());
  }

  @Transactional
  public void updateSession(UpdateSessionCommand command) {
    if (duplicateSessionTitle(command.title(), command.sessionId())) {
      throw new SessionException(SessionCode.EXITS_TITLE_SESSION);
    }

    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));

    validationStatusIsNotReady(session.getStatus());

    session.update(command.toDomain());
  }


  private boolean duplicateSessionTitle(String title, UUID sessionId) {
    if (title == null) {
      return false;
    }
    return sessionRepository.existsByTitleAndDeletedByIsNullAndTrackingIdNot(title, sessionId);
  }

  @Transactional
  public void statusUpdateSession(UpdateStateStateCommand command) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    validationStatusIsReady(session.getStatus());
    session.stateChange(SessionStatus.valueOf(command.status()));
  }

  @Transactional
  public void deleteSession(DeleteSessionCommand command) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    session.softDelete(command.username());
    removeMemberPublisher.remove(new SharedRemoveSession(session.getTrackingId()));
  }

  @Transactional
  public void applySession(UUID sessionId, UUID userId) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
//    checkSessionApply(UUID.fromString(session.getOrganizationId()), userId);
    validationStatusIsNotReady(session.getStatus());
    session.confirm();
    sessionSeatRepository.set(sessionId.toString(), session.getTotalCount());
  }

  private void checkSessionApply(UUID organizationId, UUID userId) {
    List<OrganizationMemberRole> roles = List.of(OrganizationMemberRole.MASTER, OrganizationMemberRole.MANAGER);
    ApiResponseData<Boolean> check = organizationSessionService.checkRole(organizationId, userId, roles);

    if (!Objects.equals(check.getCode(), CommonCode.SUCCESS.getCode())) {
      throw new SessionException(SessionCode.NOT_CONNECTED_SESSION);
    }

    // 매니저 이상만 세션 승인을 할 수 있습니다.
    if (!check.getData()) {
      throw new SessionException(SessionCode.OPEN_ALLOWED_SESSION);
    }

  }

  private void validationStatusIsNotReady(SessionStatus status) {
    if (status != SessionStatus.READY) {
      throw new SessionException(SessionCode.STATUS_NOT_READY_SESSION);
    }
  }

  private void validationStatusIsReady(SessionStatus status) {
    if (status == SessionStatus.READY) {
      throw new SessionException(SessionCode.STATUS_READY_SESSION);
    }
  }


}
