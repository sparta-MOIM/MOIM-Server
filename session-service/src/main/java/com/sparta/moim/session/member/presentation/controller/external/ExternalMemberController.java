package com.sparta.moim.session.member.presentation.controller.external;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.member.application.MemberService;
import com.sparta.moim.session.member.application.dto.command.JoinMemberCommand;
import com.sparta.moim.session.member.application.dto.command.LeaveMemberCommand;
import com.sparta.moim.session.member.presentation.dto.request.RemoveMemberRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class ExternalMemberController {
  private final MemberService memberService;

  @PostMapping("/{sessionId}/join")
  public void joinMember(@PathVariable UUID sessionId, @AuthenticationPrincipal CustomUserDetails user) {
    memberService.joinMember(new JoinMemberCommand(sessionId, user.getUsername()));
  }

  @DeleteMapping("/{sessionId}/leave")
  public void leaveMember(@PathVariable UUID sessionId, @AuthenticationPrincipal CustomUserDetails user) {
    memberService.leaveMember(new LeaveMemberCommand(sessionId, user.getUsername()));
  }

  @DeleteMapping("/{sessionId}/remove")
  public void removeMembers(@PathVariable UUID sessionId, @RequestBody @Valid RemoveMemberRequest request) {
    memberService.removeMember(request.toCommand(sessionId));
  }


}
