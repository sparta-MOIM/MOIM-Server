package com.sparata.moim.gathering.member.presentation.controller;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparata.moim.gathering.member.application.dto.command.JoinGatheringCommand;
import com.sparata.moim.gathering.member.application.dto.command.LeaveGatheringCommand;
import com.sparata.moim.gathering.member.application.service.MemberService;
import com.sparata.moim.gathering.member.presentation.dto.request.RemoveGatheringRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
  public void joinGathering(@PathVariable UUID gatheringId, @AuthenticationPrincipal CustomUserDetails userInfo) {
    memberService.joinGathering(new JoinGatheringCommand(gatheringId, userInfo));
  }

  @DeleteMapping("/{gatheringId}/leave")
  public void leaveGathering(@PathVariable UUID gatheringId, @AuthenticationPrincipal CustomUserDetails userInfo) {
    memberService.leaveGathering(new LeaveGatheringCommand(gatheringId, userInfo));
  }

  // 주인만 삭제가능
  @DeleteMapping("/{gatheringId}/remove")
  public void removeGathering(@PathVariable UUID gatheringId, @RequestBody @Valid RemoveGatheringRequest request) {
    memberService.removeGathering(request.toCommand(gatheringId));
  }


}
