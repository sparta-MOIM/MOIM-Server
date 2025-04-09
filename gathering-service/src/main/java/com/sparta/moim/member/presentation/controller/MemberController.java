package com.sparta.moim.member.presentation.controller;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.member.application.dto.command.JoinGatheringCommand;
import com.sparta.moim.member.application.dto.command.LeaveGatheringCommand;
import com.sparta.moim.member.application.service.MemberService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gathering/member")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService recruitService;

  @PostMapping("/{gatheringId}")
  public void joinGathering(@PathVariable UUID gatheringId, @AuthenticationPrincipal CustomUserDetails userInfo) {
    recruitService.joinGathering(new JoinGatheringCommand(gatheringId, userInfo));
  }

  @DeleteMapping("/{gatheringId}/leave")
  public void leaveGathering(@PathVariable UUID gatheringId, @AuthenticationPrincipal CustomUserDetails userInfo) {
    recruitService.leaveGathering(new LeaveGatheringCommand(gatheringId, userInfo));
  }

  // 주인만 삭제가능
  @DeleteMapping("/remove")
  public void removeGathering() {
    recruitService.removeGathering();
  }


}
