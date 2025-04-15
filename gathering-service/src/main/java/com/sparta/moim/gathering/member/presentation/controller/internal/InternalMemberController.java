package com.sparta.moim.gathering.member.presentation.controller.internal;

import com.sparta.moim.gathering.member.application.service.InternalMemberService;
import com.sparta.moim.gathering.member.presentation.dto.response.GetMemberListResponse;
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


  /**
   * 주어진 모임 ID에 해당하는 모든 멤버 정보를 조회하여 반환합니다.
   *
   * @param gatheringId 멤버를 조회할 모임의 UUID
   * @return 멤버 이름과 유형 정보를 담은 응답 DTO 리스트
   */
  @GetMapping("/{gatheringId}")
  public List<GetMemberListResponse> findMembers(@PathVariable UUID gatheringId) {
    return getMembers(gatheringId);
  }

  /**
   * 주어진 모임 ID에 해당하는 모든 멤버 정보를 조회하여 응답 DTO 리스트로 반환합니다.
   *
   * @param gatheringId 멤버를 조회할 모임의 UUID
   * @return 멤버 이름과 유형이 포함된 GetMemberListResponse 객체 리스트
   */
  private List<GetMemberListResponse> getMembers(UUID gatheringId) {
    return internalMemberService.findMembers(gatheringId).stream()
        .map(g -> new GetMemberListResponse(g.name(), g.type())).toList();
  }
}
