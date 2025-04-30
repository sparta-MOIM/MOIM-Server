package com.sparta.moim.gathering.gathering.presentation.contoller.Internal;

import com.sparta.moim.gathering.gathering.application.service.struct.InternalMemberService;
import com.sparta.moim.gathering.gathering.presentation.dto.response.GetMemberListResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/member")
@RequiredArgsConstructor
public class InternalMemberController {
  private final InternalMemberService internalMemberService;


  @GetMapping("/{gatheringId}")
  public List<GetMemberListResponse> findMembers(@PathVariable UUID gatheringId) {
    return getMembers(gatheringId);
  }

  private List<GetMemberListResponse> getMembers(UUID gatheringId) {
    return internalMemberService.findMembers(gatheringId).stream()
        .map(g -> new GetMemberListResponse(g.name(), g.type())).toList();
  }
}
