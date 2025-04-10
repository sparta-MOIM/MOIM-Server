package com.moim.post.infrastructure.persistence.repository.QueryDsl;

import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.feed.QFeed;
import com.moim.post.domain.repository.query.FeedQueryRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedQueryDslRepository implements FeedQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QFeed feed = QFeed.feed;

  public Optional<Feed> findFeed(UUID id) {
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(feed)
            .where(feed.trackingId.eq(id))
            .fetchOne()
    );
  }

  public Page<Feed> searchFeed(
      Optional<UUID> organizationId,
      Optional<String> word,
      Pageable pageable
  ) {
    QueryResults<Feed> results = jpaQueryFactory
        .selectFrom(feed)
        .where(
            organizationIdFilter(organizationId),
            wordFilter(word)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(pageable.getSort().stream()
            .map(order -> order.isAscending() ? feed.createdAt.asc() : feed.createdAt.desc())
            .toArray(OrderSpecifier[]::new))
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

  private BooleanExpression organizationIdFilter(Optional<UUID> organizationId) {
    return organizationId.map(feed.organizationId::eq).orElse(null);
  }

  private BooleanExpression wordFilter(Optional<String> word) {
    return word.map(w ->
        feed.title.containsIgnoreCase(w).or(feed.content.containsIgnoreCase(w))
    ).orElse(null);
  }

}
