package com.moim.post.application.usecase;

import com.moim.post.application.query.FindQuery;
import com.moim.post.application.query.SearchFeedQuery;
import com.moim.post.application.query.SearchVoteQuery;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;
import org.springframework.data.domain.Page;

public interface PostQueryUseCase {
  Feed findFeed(FindQuery query);

  Page<Feed> searchFeed(
      SearchFeedQuery query,
      int page,
      int size,
      String sortType
  );

  Vote findVote(FindQuery query);

  Page<Vote> searchVote(
      SearchVoteQuery query,
      int page,
      int size,
      String sortType
  );

  Boolean isValidFeed(FindQuery query);
}
