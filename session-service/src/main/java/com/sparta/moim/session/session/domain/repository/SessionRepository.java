package com.sparta.moim.session.session.domain.repository;

import com.sparta.moim.session.session.domain.entity.Session;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
  /**
 * 주어진 Session 객체를 저장하고, 저장된 Session 객체를 반환합니다.
 *
 * @param session 저장할 Session 객체
 * @return 저장된 Session 객체
 */
Session save(Session session);

  /**
 * 주어진 추적 ID를 사용하여 삭제되지 않은(삭제일자가 null인) 세션을 조회합니다.
 *
 * @param id 검색할 세션의 추적 ID
 * @return 조회된 세션을 Optional로 감싸 반환하며, 세션이 존재하지 않을 경우 빈 Optional을 반환합니다.
 */
Optional<Session> findByTrackingIdAndDeletedAtIsNull(UUID id);

  /**
 * 지정된 제목을 가진, 삭제되지 않은 세션이 존재하는지 확인합니다.
 *
 * @param title 검사할 세션 제목
 * @return 해당 제목의 세션이 삭제되지 않은 상태로 존재하면 {@code true}, 그렇지 않으면 {@code false}
 */
boolean existsByTitleAndDeletedByIsNull(String title);

  /**
 * 지정된 제목을 갖는 세션 중, 삭제되지 않았으며(삭제자를 나타내는 값이 null인 경우) 지정된 추적 ID와 일치하지 않는 세션이 존재하는지 확인합니다.
 *
 * @param title 조회할 세션의 제목
 * @param sessionId 조회에서 제외할 세션의 추적 ID
 * @return 조건을 만족하는 세션이 존재하면 true, 그렇지 않으면 false
 */
boolean existsByTitleAndDeletedByIsNullAndTrackingIdNot(String title, UUID sessionId);

}
