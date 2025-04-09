package com.moim.post.presentation;

import com.moim.post.application.usecase.PostQueryUseCase;
import com.moim.post.domain.feed.Feed;
import com.moim.post.presentation.mapper.PostPresentationMapper;
import com.moim.post.presentation.request.SearchFeedRequest;
import com.moim.post.presentation.response.FeedResponse;
import com.sparta.moim.common.response.ApiResponseData;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostQueryController {

  private final PostQueryUseCase useCase;
  private final PostPresentationMapper mapper;

  @GetMapping("/feeds/{id}")
  public ResponseEntity<ApiResponseData<FeedResponse>> findFeed(@PathVariable final String id) {
    log.info("Feed 조회 요청: {}", id);
    Feed feed = useCase.findFeed(mapper.toQuery(UUID.fromString(id)));
    FeedResponse response = mapper.toResponse(feed);
    log.info("Feed 조회 완료");
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }

  @GetMapping("/feeds")
  public ResponseEntity<ApiResponseData<Page<FeedResponse>>> searchFeeds(
      @ModelAttribute final SearchFeedRequest request,
      @RequestParam(defaultValue = "0") final int page,
      @RequestParam(defaultValue = "10") final int size,
      @RequestParam(defaultValue = "createdAt") final String sortType
  ) {
    Page<FeedResponse> response = useCase.searchFeed(mapper.toQuery(request), page, size, sortType)
        .map(mapper::toResponse);
    return ResponseEntity.ok().body(ApiResponseData.success(response));
  }


}
