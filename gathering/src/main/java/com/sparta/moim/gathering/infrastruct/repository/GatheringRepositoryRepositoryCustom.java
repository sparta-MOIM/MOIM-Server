package com.sparta.moim.gathering.infrastruct.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.gathering.domain.dto.criteria.SearchGatheringCriteria;
import com.sparta.moim.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.domain.entity.QGathering;
import com.sparta.moim.gathering.domain.repository.GatheringRepositoryCustom;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GatheringRepositoryRepositoryCustom implements GatheringRepositoryCustom {
  private final JPAQueryFactory query;
  private final QGathering gathering = QGathering.gathering;

  @Override
  public Pagination<Gathering> searchGathering(SearchGatheringCriteria criteria) {
    String username = criteria.username();
    String role = criteria.role();
    List<UUID> gatheringIds = findGatheringIds(username, role);

    List<Gathering> content = query.select(gathering)
        .where(
            nullCheckGatheringId(gatheringIds),
            gathering.status.eq(criteria.status()),
            gathering.createdDateTime.between(criteria.startTime(), criteria.endTime()),
            nullCheckGatheringDeleted(criteria.isDeleted(), criteria.role()))
        .from(gathering)
        .offset((long) (criteria.page() - 1) * criteria.size())
        .limit(criteria.size())
        .fetch();

    Long total = query.select(gathering.count())
        .where(
            gathering.id.in(gatheringIds),
            gathering.status.eq(criteria.status()),
            gathering.createdDateTime.between(criteria.startTime(), criteria.endTime()),
            nullCheckGatheringDeleted(criteria.isDeleted(), criteria.role()))
        .from(gathering).fetchOne();

    return Pagination.of(criteria.page(), criteria.size(), total != null ? total : 0, content);
  }

  // 삭제 여부
  private Predicate nullCheckGatheringDeleted(Boolean isDeleted, String role) {
    isDeleted = isActiveUser(isDeleted, role);
    return isDeleted == null ? null : isDeleted ? gathering.deletedAt.isNotNull() : gathering.deletedAt.isNull();
  }

  private static Boolean isActiveUser(Boolean isDeleted, String role) {
    if (role.equals("USER")) {
      isDeleted = false;
    }
    return isDeleted;
  }

  private Predicate nullCheckGatheringId(List<UUID> gatheringIds) {
    return gatheringIds == null ? null : gathering.id.in(gatheringIds);
  }

  private List<UUID> findGatheringIds(String username, String role) {
    // role타입이 유저 정보가 아니면 조회하지 않는다.
    if (!role.equals("USER")) {
      return null;
    }

    return query.select(gathering.id).from(gathering)
        .where(gathering.owner.eq(username)).stream().toList();

  }
}
