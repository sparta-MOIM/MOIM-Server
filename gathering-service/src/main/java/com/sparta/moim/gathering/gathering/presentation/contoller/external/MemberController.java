package com.sparta.moim.gathering.gathering.presentation.contoller.external;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.presentation.dto.request.RemoveGatheringRequest;
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

  @PostMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<Void>> joinGathering(@PathVariable UUID gatheringId,
                                                             @AuthenticationPrincipal CustomUserDetails userInfo) {
    memberService.joinGathering(new JoinGatheringCommand(gatheringId, userInfo));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  @DeleteMapping("/{gatheringId}/leave")
  public ResponseEntity<ApiResponseData<Void>> leaveGathering(@PathVariable UUID gatheringId,
                                                              @AuthenticationPrincipal CustomUserDetails userInfo) {
    memberService.leaveGathering(new LeaveGatheringCommand(gatheringId, userInfo));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  // 주인만 삭제가능
  @DeleteMapping("/{gatheringId}/remove")
  public ResponseEntity<ApiResponseData<Void>> removeGathering(@PathVariable UUID gatheringId,
                                                               @RequestBody @Valid RemoveGatheringRequest request,
                                                               @AuthenticationPrincipal CustomUserDetails details) {
    memberService.removeGathering(request.toCommand(gatheringId, details.getUsername()));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }


}
