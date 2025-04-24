package com.sparta.moim.gathering.gathering.infrastructure.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.gathering.gathering.domain.dto.criteria.SearchGatheringCriteria;
import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.gathering.domain.entity.QGathering;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepositoryCustom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GatheringRepositoryRepositoryCustomImpl implements GatheringRepositoryCustom {
  private final JPAQueryFactory query;
  private final QGathering gathering = QGathering.gathering;

  @Override
  public Pagination<Gathering> searchGathering(SearchGatheringCriteria criteria) {
//    List<Long> gatheringIds = findGatheringIds(username, role);

    List<Gathering> content = query.select(gathering)
        .where(
//            nullCheckGatheringId(gatheringIds),
            gathering.status.eq(criteria.status()),
            nullCheckStartTime(criteria.startTime(), criteria.endTime()),
            nullCheckGatheringDeleted(criteria.isDeleted(), criteria.role())
        )
        .from(gathering)
        .offset((long) criteria.page() * criteria.size())
        .limit(criteria.size())
        .orderBy(gathering.createdAt.desc())
        .fetch();

    Long total = query.select(gathering.count())
        .where(
//            nullCheckGatheringId(gatheringIds),
            gathering.status.eq(criteria.status()),
            nullCheckStartTime(criteria.startTime(), criteria.endTime()),
            nullCheckGatheringDeleted(criteria.isDeleted(), criteria.role()))
        .from(gathering).fetchOne();

    return Pagination.of(criteria.page(), criteria.size(), total != null ? total : 0, content);
  }

  private Predicate nullCheckStartTime(LocalDateTime startTime, LocalDateTime endTime) {
    if (endTime == null) {
      endTime = LocalDateTime.now();
    }

    if (startTime == null) {
      return null;
    }

    return gathering.createdAt.between(startTime, endTime);
  }

  // 삭제 여부
  private Predicate nullCheckGatheringDeleted(Boolean isDeleted, String role) {
    isDeleted = isActiveUser(isDeleted, role);
    return isDeleted == null ? null : isDeleted ? gathering.deletedAt.isNotNull() : gathering.deletedAt.isNull();
  }

  private static Boolean isActiveUser(Boolean isDeleted, String role) {
    if ("USER".equals(role)) {
      isDeleted = false;
    }
    return isDeleted;
  }

  private Predicate nullCheckGatheringId(List<Long> gatheringIds) {
    return gatheringIds == null ? null : gathering.id.in(gatheringIds);
  }

  private List<Long> findGatheringIds(UUID username, String role) {
    if (role == null || "USER".equals(role)) {
      return null;
    }

    return query.select(gathering.id).from(gathering)
        .where(gathering.owner.eq(username)).stream().toList();

  }
}
