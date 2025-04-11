package com.sparta.moim.session.member.presentation.controller.internal;

import com.sparta.moim.session.member.application.MemberService;
import com.sparta.moim.session.member.application.dto.command.GetMemberCommand;
import com.sparta.moim.session.member.presentation.dto.response.GetMemberResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/session")
@RequiredArgsConstructor
public class InternalMemberController {
  private final MemberService memberService;

  @GetMapping("/{sessionId}")
  public GetMemberResponse getMember(@PathVariable UUID sessionId) {
    return GetMemberResponse.get(memberService.getMember(new GetMemberCommand(sessionId)));
  }
}
