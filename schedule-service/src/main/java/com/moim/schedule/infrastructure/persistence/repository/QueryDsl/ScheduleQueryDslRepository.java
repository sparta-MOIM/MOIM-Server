package com.moim.schedule.infrastructure.persistence.repository.QueryDsl;

import com.moim.schedule.domain.QSchedule;
import com.moim.schedule.domain.Schedule;
import com.moim.schedule.domain.repository.query.ScheduleQueryRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleQueryDslRepository implements ScheduleQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QSchedule schedule = QSchedule.schedule;

  @Override
  public Optional<Schedule> findSchedule(UUID id) {
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(schedule)
            .where(schedule.trackingId.eq(id))
            .fetchOne()
    );
  }

  @Override
  public Page<Schedule> searchSchedule(
      Optional<UUID> organizationId,
      Optional<String> word,
      Optional<LocalDateTime> start,
      Optional<LocalDateTime> end,
      Pageable pageable
  ) {
    QueryResults<Schedule> results = jpaQueryFactory
        .selectFrom(schedule)
        .where(
            organizationIdFilter(organizationId),
            wordFilter(word),
            periodFilter(start,end)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(pageable.getSort().stream()
            .map(order -> order.isAscending() ? schedule.createdAt.asc() : schedule.createdAt.desc())
            .toArray(OrderSpecifier[]::new))
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

  private BooleanExpression organizationIdFilter(Optional<UUID> organizationId) {
    return organizationId.map(schedule.organizationId::eq).orElse(null);
  }

  private BooleanExpression wordFilter(Optional<String> word) {
    return word.map(w ->
        schedule.title.containsIgnoreCase(w).or(schedule.content.containsIgnoreCase(w))
    ).orElse(null);
  }

  private BooleanExpression periodFilter(Optional<LocalDateTime> start, Optional<LocalDateTime> end) {
    return Objects.requireNonNull(start.map(schedule.period.end::goe).orElse(null)).and(end.map(schedule.period.start::loe).orElse(null));
  }

}
