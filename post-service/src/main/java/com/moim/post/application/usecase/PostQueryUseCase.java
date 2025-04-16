package com.moim.post.application.usecase;

import com.moim.post.application.query.FindQuery;
import com.moim.post.application.query.SearchFeedQuery;
import com.moim.post.application.query.SearchVoteQuery;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;
import com.sparta.moim.common.security.CustomUserDetails;
import org.springframework.data.domain.Page;

public interface PostQueryUseCase {
  Feed findFeed(
      CustomUserDetails userDetails,
      FindQuery query
  );

  Page<Feed> searchFeed(
      CustomUserDetails userDetails,
      SearchFeedQuery query,
      int page,
      int size,
      String sortType
  );

  Vote findVote(
      CustomUserDetails userDetails,
      FindQuery query
  );

  Page<Vote> searchVote(
      CustomUserDetails userDetails,
      SearchVoteQuery query,
      int page,
      int size,
      String sortType
  );

  Boolean isValidFeed(FindQuery query);
}
