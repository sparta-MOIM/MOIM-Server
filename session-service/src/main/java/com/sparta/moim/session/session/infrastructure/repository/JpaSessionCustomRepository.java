package com.sparta.moim.session.session.infrastructure.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.session.session.domain.dto.crtria.SearchSessionCriteria;
import com.sparta.moim.session.session.domain.entity.QSession;
import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.SessionCustomRepository;
import com.sparta.moim.session.shared.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaSessionCustomRepository implements SessionCustomRepository {
  private final JPAQueryFactory query;
  private final QSession session = QSession.session;

  @Override
  public Pagination<Session> searchSession(SearchSessionCriteria criteria) {

    List<Session> content = query.select(session)
        .where(
            nullCheckStatus(criteria.status()),
            nullCheckOpenTime(criteria.openTime(), criteria.closeTime()),
            nullCheckTitle(criteria.title()),
            nullCheckReason(criteria.reason()),
            nullCheckGatheringDeleted(criteria.isDeleted(), criteria.role())
        )
        .from(session)
        .offset((long) criteria.page() * criteria.size())
        .limit(criteria.size())
        .orderBy(session.createdAt.desc())
        .fetch();

    Long total = query.select(session.count())
        .where(
            nullCheckStatus(criteria.status()),
            nullCheckTitle(criteria.title()),
            nullCheckReason(criteria.reason()),
            nullCheckOpenTime(criteria.openTime(), criteria.closeTime()),
            nullCheckGatheringDeleted(criteria.isDeleted(), criteria.role()))
        .from(session).fetchOne();

    return Pagination.of(criteria.page(), criteria.size(), total != null ? total : 0, content);

  }

  private Predicate nullCheckStatus(SessionStatus status) {
    if(status == null) return null;
    return session.status.eq(status);
  }

  private Predicate nullCheckReason(String reason) {
    if (reason == null) {
      return null;
    }

    return  session.reason.contains(reason);
  }

  private Predicate nullCheckTitle(String title) {
    if (title == null) {
      return null;
    }

    return  session.title.contains(title);
  }

  private Predicate nullCheckGatheringDeleted(Boolean isDeleted, String role) {
    if (role.equals("USER")) {
      isDeleted = false;
    }

    return isDeleted == null ? null : isDeleted ? session.deletedBy.isNotNull() : session.deletedBy.isNull();
  }

  private Predicate nullCheckOpenTime(LocalDateTime openTime, LocalDateTime closeTime) {
    if (closeTime == null) {
      closeTime = LocalDateTime.now();
    }

    if (openTime == null) {
      return null;
    }

    return session.createdAt.between(openTime, closeTime);
  }

}
