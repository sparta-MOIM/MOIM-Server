package com.moim.post.presentation;

import com.moim.post.application.usecase.PostCommandUseCase;
import com.moim.post.domain.feed.Feed;
import com.moim.post.domain.vote.Vote;
import com.moim.post.presentation.mapper.PostPresentationMapper;
import com.moim.post.presentation.request.CreateFeedRequest;
import com.moim.post.presentation.request.CreateVoteRequest;
import com.moim.post.presentation.response.FeedResponse;
import com.moim.post.presentation.response.VoteResponse;
import com.sparta.moim.common.response.ApiResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostCommandController {

  private final PostCommandUseCase useCase;
  private final PostPresentationMapper mapper;

  @PostMapping("/feeds")
  public ResponseEntity<ApiResponseData<FeedResponse>> createFeed(
      @Valid @RequestBody CreateFeedRequest request
  ) {
    log.info("Feed 생성 요청: {}", request.toString());
    Feed feed = useCase.createFeed(mapper.toCommand(request));
    FeedResponse response = mapper.toResponse(feed);
    log.info("Feed 생성 및 저장 완료: {}", response.id().toString());
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }

  @PostMapping("/votes")
  public ResponseEntity<ApiResponseData<VoteResponse>> createVotes(
      @Valid @RequestBody CreateVoteRequest request
  ) {
    log.info("Vote 생성 요청: {}", request.toString());
    Vote vote = useCase.createVote(mapper.toCommand(request));
    VoteResponse response = mapper.toResponse(vote);
    log.info("Vote 생성 및 저장 완료: {}", response.id().toString());
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }

}
