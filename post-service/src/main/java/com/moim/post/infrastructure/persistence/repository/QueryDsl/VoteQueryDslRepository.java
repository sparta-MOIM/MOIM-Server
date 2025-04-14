package com.moim.post.infrastructure.persistence.repository.querydsl;

import com.moim.post.domain.repository.query.VoteQueryRepository;
import com.moim.post.domain.vote.QVote;
import com.moim.post.domain.vote.Vote;
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
public class VoteQueryDslRepository implements VoteQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QVote vote = QVote.vote;

  @Override
  public Optional<Vote> findVote(UUID id) {
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(vote)
            .where(vote.trackingId.eq(id))
            .fetchOne()
    );
  }

  @Override
  public Page<Vote> searchVote(
      Optional<UUID> organizationId,
      Optional<String> word,
      Optional<LocalDateTime> start,
      Optional<LocalDateTime> end,
      Pageable pageable
  ) {
    QueryResults<Vote> results = jpaQueryFactory
        .selectFrom(vote)
        .where(
            organizationIdFilter(organizationId),
            wordFilter(word),
            periodFilter(start,end)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(pageable.getSort().stream()
            .map(order -> order.isAscending() ? vote.createdAt.asc() : vote.createdAt.desc())
            .toArray(OrderSpecifier[]::new))
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

  private BooleanExpression organizationIdFilter(Optional<UUID> organizationId) {
    return organizationId.map(vote.organizationId::eq).orElse(null);
  }

  private BooleanExpression wordFilter(Optional<String> word) {
    return word.map(w ->
        vote.title.containsIgnoreCase(w).or(vote.content.containsIgnoreCase(w))
    ).orElse(null);
  }

  private BooleanExpression periodFilter(Optional<LocalDateTime> start, Optional<LocalDateTime> end) {
    return Objects.requireNonNull(start.map(vote.period.end::goe).orElse(null)).and(end.map(vote.period.start::loe).orElse(null));
  }

}
