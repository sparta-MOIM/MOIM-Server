package com.sparta.moim.gathering.member.presentation.controller.external;

import com.sparta.moim.gathering.member.application.dto.command.JoinGatheringCommand;
import com.sparta.moim.gathering.member.application.dto.command.LeaveGatheringCommand;
import com.sparta.moim.gathering.member.application.service.MemberService;
import com.sparta.moim.gathering.member.presentation.dto.request.RemoveGatheringRequest;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gathering/member")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  /**
   * 사용자가 지정된 모임에 참여합니다.
   *
   * @param gatheringId 참여할 모임의 고유 식별자
   * @param userInfo 인증된 사용자 정보
   * @return 성공 시 내용이 없는 표준 API 응답
   */
  @PostMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<Void>> joinGathering(@PathVariable UUID gatheringId,
                                                             @AuthenticationPrincipal CustomUserDetails userInfo) {
    memberService.joinGathering(new JoinGatheringCommand(gatheringId, userInfo));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  /**
   * 사용자가 특정 모임에서 탈퇴하도록 처리합니다.
   *
   * @param gatheringId 탈퇴할 모임의 고유 식별자
   * @param userInfo 인증된 사용자 정보
   * @return 성공 시 내용 없는 표준 API 응답
   */
  @DeleteMapping("/{gatheringId}/leave")
  public ResponseEntity<ApiResponseData<Void>> leaveGathering(@PathVariable UUID gatheringId,
                                                              @AuthenticationPrincipal CustomUserDetails userInfo) {
    memberService.leaveGathering(new LeaveGatheringCommand(gatheringId, userInfo));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  /**
   * 모임의 소유자가 해당 모임을 삭제합니다.
   *
   * @param gatheringId 삭제할 모임의 고유 식별자
   * @param request 모임 삭제 요청 정보
   * @return 삭제 성공 시 빈 데이터를 포함한 200 OK 응답
   */
  @DeleteMapping("/{gatheringId}/remove")
  public ResponseEntity<ApiResponseData<Void>> removeGathering(@PathVariable UUID gatheringId,
                                                               @RequestBody @Valid RemoveGatheringRequest request,
                                                               @AuthenticationPrincipal CustomUserDetails details) {
    memberService.removeGathering(request.toCommand(gatheringId, details.getUsername()));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }


}
