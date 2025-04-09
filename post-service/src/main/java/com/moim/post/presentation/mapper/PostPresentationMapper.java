package com.moim.post.presentation.mapper;

import com.moim.post.application.command.CreateFeedCommand;
import com.moim.post.domain.feed.Feed;
import com.moim.post.presentation.request.CreateFeedRequest;
import com.moim.post.presentation.response.FeedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostPresentationMapper {
  CreateFeedCommand toCommand(CreateFeedRequest request);

  @Mapping(source = "trackingId", target = "id")
  FeedResponse toResponse(Feed feed);
}
