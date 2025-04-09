package com.moim.post.application.usecase;

import com.moim.post.application.query.FindFeedQuery;
import com.moim.post.application.query.SearchFeedQuery;
import com.moim.post.domain.feed.Feed;
import org.springframework.data.domain.Page;

public interface PostQueryUseCase {
  Feed findFeed(FindFeedQuery query);

  Page<Feed> searchFeed(
      SearchFeedQuery query,
      int page,
      int size,
      String sortType
  );
}
