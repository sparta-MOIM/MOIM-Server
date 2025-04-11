package com.moim.post.presentation.mapper;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.application.command.CreateVoteCommand;
import com.moim.post.application.command.DeleteCommand;
import com.moim.post.application.command.UpdateFeedCommand;
import com.moim.post.application.command.UpdateVoteCommand;
import com.moim.post.application.query.FindQuery;
import com.moim.post.application.query.SearchFeedQuery;
import com.moim.post.application.query.SearchVoteQuery;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;
import com.moim.post.presentation.request.CreateFeedRequest;
import com.moim.post.presentation.request.CreateVoteRequest;
import com.moim.post.presentation.request.SearchFeedRequest;
import com.moim.post.presentation.request.SearchVoteRequest;
import com.moim.post.presentation.request.UpdateFeedRequest;
import com.moim.post.presentation.request.UpdateVoteRequest;
import com.moim.post.presentation.response.FeedResponse;
import com.moim.post.presentation.response.ValidationResponse;
import com.moim.post.presentation.response.VoteResponse;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostPresentationMapper {
  CreateFeedCommand toCommand(CreateFeedRequest request);

  @Mapping(source = "trackingId", target = "id")
  FeedResponse toResponse(Feed feed);

  CreateVoteCommand toCommand(CreateVoteRequest request);

  @Mapping(source = "trackingId", target = "id")
  @Mapping(source = "period.start", target = "start")
  @Mapping(source = "period.end", target = "end")
  VoteResponse toResponse(Vote vote);

  FindQuery toQuery(UUID id);

  SearchFeedQuery toQuery(SearchFeedRequest request);

  SearchVoteQuery toQuery(SearchVoteRequest request);

  UpdateFeedCommand toCommand(UpdateFeedRequest request);

  UpdateVoteCommand toCommand(UpdateVoteRequest request);

  DeleteCommand toCommand(UUID id);

  ValidationResponse toResponse(Boolean result);
}
