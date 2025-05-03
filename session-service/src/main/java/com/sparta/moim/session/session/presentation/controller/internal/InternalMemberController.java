package com.sparta.moim.session.session.presentation.controller.internal;

import com.sparta.moim.session.session.application.service.MemberService;
import com.sparta.moim.session.session.application.dto.command.GetMemberCommand;
import com.sparta.moim.session.session.application.dto.result.GetMemberListResult;
import com.sparta.moim.session.session.presentation.dto.response.GetMemberListResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
  public List<GetMemberListResponse> getMember(@PathVariable UUID sessionId) {
    return getMemberServiceMember(sessionId).stream().map(GetMemberListResponse::new).collect(Collectors.toList());
  }

  private List<GetMemberListResult> getMemberServiceMember(UUID sessionId) {
    return memberService.getMember(new GetMemberCommand(sessionId));
  }
}
