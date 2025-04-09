package com.moim.post.application;

import com.moim.post.application.query.FindFeedQuery;
import com.moim.post.application.query.SearchFeedQuery;
import com.moim.post.application.usecase.PostQueryUseCase;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.repository.query.FeedQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryService implements PostQueryUseCase {

  private final FeedQueryRepository feedRepository;

  @Override
  public Feed findFeed(FindFeedQuery query) {
    // todo: 예외처리하기
    return feedRepository.findFeed(query.id()).orElseThrow(null);
  }

  @Override
  public Page<Feed> searchFeed(
      SearchFeedQuery query,
      int page,
      int size,
      String sortType
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, sortType));
    return feedRepository.searchFeed(
        query.organizationId(),
        query.word(),
        pageable
    );
  }
}
