package com.sparta.moim.session.session.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.session.session.application.dto.DeleteSessionCommand;
import com.sparta.moim.session.session.application.dto.command.CreateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.SearchSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import com.sparta.moim.session.session.application.dto.command.UpdateStateStateCommand;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import com.sparta.moim.session.session.application.dto.result.SearchSessionResult;
import com.sparta.moim.session.session.application.exception.SessionException;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import com.sparta.moim.session.session.domain.error.code.SessionCode;
import com.sparta.moim.session.session.domain.repository.SessionCustomRepository;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;
  private final SessionCustomRepository sessionCustomRepository;

  /**
   * 새 세션을 생성하고 결과를 반환한다.
   *
   * 입력된 세션 생성 명령을 활용하여 도메인 객체를 생성, 저장한 후 결과 객체를 반환한다.
   * 동일한 제목의 세션이 이미 존재하는 경우, {@link SessionException}이
   * {@code SessionCode.EXITS_TITLE_SESSION} 코드와 함께 발생한다.
   *
   * @param command 세션 생성을 위한 명령 객체
   * @return 생성된 세션의 결과 객체
   */
  public CreateSessionResult createSession(CreateSessionCommand command) {
    if (sessionRepository.existsByTitleAndDeletedByIsNull(command.title())) {
      throw new SessionException(SessionCode.EXITS_TITLE_SESSION);
    }
    return CreateSessionResult.create(sessionRepository.save(command.toDomain()));
  }

  /**
   * 주어진 세션 추적 ID를 기반으로 삭제되지 않은 세션 정보를 조회합니다.
   *
   * 해당 ID에 대응하는 세션이 존재하지 않을 경우, SessionCode.NOT_FOUND_SESSION 코드와 함께
   * SessionException을 발생시킵니다.
   *
   * @param sessionId 조회할 세션의 추적 ID
   * @return 조회된 세션 정보를 포함하는 GetSessionResult 객체
   * @throws SessionException 세션을 찾을 수 없는 경우
   */
  @Transactional(readOnly = true)
  public GetSessionResult getSession(UUID sessionId) {
    return GetSessionResult.get(sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION)));
  }

  /**
   * 주어진 검색 조건에 맞는 세션들을 조회하고, 페이징 처리된 결과를 반환한다.
   *
   * 이 메서드는 SearchSessionCommand에 담긴 조건을 바탕으로 세션을 검색하여,
   * 검색된 세션 목록, 전체 개수, 현재 페이지 번호 및 페이지 크기를 포함하는 SearchSessionResult 객체를 생성한다.
   *
   * @param command 검색 조건 정보를 담은 SearchSessionCommand 객체
   * @return 조회된 세션 목록과 페이징 정보를 포함한 SearchSessionResult 객체
   */
  @Transactional(readOnly = true)
  public SearchSessionResult searchSession(SearchSessionCommand command) {
    Pagination<Session> sessions = sessionCustomRepository.searchSession(command.toCriteria());
    return SearchSessionResult.search(sessions.getContent(),
        sessions.getTotal(),
        sessions.getPage(),
        sessions.getSize());
  }

  /**
   * 주어진 업데이트 커맨드를 사용하여 세션을 갱신합니다.
   *
   * <p>
   * 먼저, 커맨드에 포함된 타이틀의 중복 여부를 확인하며, 중복된 경우 {@code SessionException}({@code SessionCode.EXITS_TITLE_SESSION})이 발생합니다.
   * 이후, 유효한 세션을 조회하고, 해당 세션이 없으면 {@code SessionException}({@code SessionCode.NOT_FOUND_SESSION})을 발생시킵니다.
   * 세션의 상태가 업데이트 가능한 상태인지 검증하여 부적합할 경우 {@code SessionException}({@code SessionCode.STATUS_NOT_READY_SESSION})을 발생시킵니다.
   * 마지막으로, 커맨드에서 제공하는 도메인 데이터를 기준으로 세션을 갱신합니다.
   * </p>
   *
   * @param command 업데이트할 세션의 정보를 포함한 명령 객체
   */
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


  /**
   * 주어진 세션 제목의 중복 여부를 확인합니다.
   *
   * 제목이 {@code null}인 경우, 중복 검사는 수행되지 않으며 {@code false}를 반환합니다.
   * 그렇지 않으면, 지정된 세션 식별자({@code sessionId})를 제외한 활성 세션 중 동일한 제목을 가진 세션이 존재하는지 검사합니다.
   *
   * @param title 중복 여부를 확인할 세션 제목; {@code null}인 경우 중복 검사의 대상이 아닙니다.
   * @param sessionId 중복 검사에서 제외할 현재 세션의 고유 식별자
   * @return 동일 제목을 가진 활성 세션이 존재하면 {@code true}, 그렇지 않으면 {@code false}
   */
  private boolean duplicateSessionTitle(String title, UUID sessionId) {
    if(title == null) {
      return false;
    }
    return sessionRepository.existsByTitleAndDeletedByIsNullAndTrackingIdNot(title, sessionId);
  }

  /**
   * 주어진 세션 상태 업데이트 커맨드를 사용하여 세션의 상태를 변경합니다.
   * 
   * <p>해당 메소드는 커맨드에 포함된 sessionId로 세션을 조회하며, 세션이 존재하지 않으면
   * SessionException(SessionCode.NOT_FOUND_SESSION)이 발생합니다. 또한, 현재 세션 상태가 업데이트가 불가능한
   * READY 상태인 경우 SessionException(SessionCode.STATUS_NOT_READY_SESSION)이 발생합니다. 조건을 만족하면,
   * 커맨드에 명시된 새 상태로 세션의 상태를 변경합니다.</p>
   *
   * @param command 세션 상태 갱신에 필요한 정보를 담은 커맨드 객체
   * @throws SessionException 세션이 존재하지 않거나 세션 상태가 READY 상태일 때 발생
   */
  @Transactional
  public void statusUpdateSession(UpdateStateStateCommand command) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));

    validationStatusIsNotReady(session.getStatus());

    session.stateChange(SessionStatus.valueOf(command.status()));
  }

  /**
   * 주어진 삭제 명령 객체를 사용하여 세션의 소프트 삭제를 수행한다.
   * <p>
   * 전달된 명령에 포함된 세션 추적 ID를 통해 삭제되지 않은 세션을 조회하며, 세션이 존재하지 않을 경우
   * SessionCode.NOT_FOUND_SESSION이 포함된 SessionException을 발생시킨다.
   * 조회된 세션은 명령에 지정된 사용자 이름을 사용하여 소프트 삭제 처리된다.
   *
   * @param command 세션 삭제에 필요한 추적 ID와 사용자 이름 정보를 포함하는 삭제 명령 객체
   */
  @Transactional
  public void deleteSession(DeleteSessionCommand command) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(command.sessionId())
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    session.softDelete(command.username());
  }

  /**
   * 주어진 세션 ID에 해당하는 세션을 확인(확정)합니다.
   *
   * 삭제되지 않은 세션을 조회하며, 해당 세션이 존재하지 않거나 세션의 상태가 준비(READY) 상태인 경우
   * {@code SessionException}을 발생시킵니다. 정상 상태의 세션인 경우 확인 처리를 진행합니다.
   *
   * @param sessionId 확인 처리할 세션의 고유 식별자
   * @throws SessionException 세션 조회 실패 또는 상태가 확인 처리에 부적합한 경우
   */
  @Transactional
  public void applySession(UUID sessionId) {
    Session session = sessionRepository.findByTrackingIdAndDeletedAtIsNull(sessionId)
        .orElseThrow(() -> new SessionException(SessionCode.NOT_FOUND_SESSION));
    validationStatusIsNotReady(session.getStatus());
    session.confirm();

  }

  /**
   * 세션 상태가 READY 상태인지 확인합니다.
   * <p>
   * 전달된 세션 상태가 READY가 아닐 경우, STATUS_NOT_READY_SESSION 코드와 함께 SessionException을 발생시킵니다.
   *
   * @param status 확인할 세션 상태
   */
  private void validationStatusIsNotReady(SessionStatus status) {
    if(status != SessionStatus.READY) {
      throw new SessionException(SessionCode.STATUS_NOT_READY_SESSION);
    }
  }

}
