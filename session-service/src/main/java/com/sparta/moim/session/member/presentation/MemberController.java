package com.sparta.moim.session.member.presentation;

import com.sparta.moim.session.member.application.MemberService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @PostMapping("/{sessionId}/join")
  public void joinMember(@PathVariable UUID sessionId) {
    memberService.joinMember(sessionId);
  }

  @PostMapping("/{sessionId}/leave")
  public void leaveMember(@PathVariable UUID sessionId) {
    memberService.leaveMember(sessionId);
  }

  @PostMapping("/{sessionId}/remove")
  public void removeMembers(@PathVariable UUID sessionId) {
    memberService.removeMember(sessionId);
  }


}
