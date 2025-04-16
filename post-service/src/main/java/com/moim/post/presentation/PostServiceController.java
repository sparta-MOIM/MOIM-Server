package com.moim.post.presentation;

import com.moim.post.application.usecase.PostQueryUseCase;
import com.moim.post.presentation.mapper.PostPresentationMapper;
import com.moim.post.presentation.response.ValidationResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/posts")
public class PostServiceController {

  private final PostQueryUseCase useCase;
  private final PostPresentationMapper mapper;

  @GetMapping("/feeds/{id}")
  public ValidationResponse isValidFeed(@PathVariable("id") final String id) {
    log.info("[Internal] Feed 유효성 요청: {}", id);
    Boolean result = useCase.isValidFeed(mapper.toQuery(UUID.fromString(id)));
    ValidationResponse response = mapper.toResponse(result);
    log.info("[Internal] Feed 유효성 검사 완료");
    return response;
  }

}
