package com.sparta.moim.session.member.presentation.controller.external;

import com.sparta.moim.common.response.ApiResponseData;
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
  public ApiResponseData<Void> joinMember(@PathVariable UUID sessionId,
                                    @AuthenticationPrincipal CustomUserDetails details) {
    memberService.joinMember(new JoinMemberCommand(sessionId, details.getUsername()));
    return ApiResponseData.success(null);
  }

  @DeleteMapping("/{sessionId}/leave")
  public ApiResponseData<Void> leaveMember(@PathVariable UUID sessionId, @AuthenticationPrincipal CustomUserDetails user) {
    memberService.leaveMember(new LeaveMemberCommand(sessionId, user.getUsername()));
    return ApiResponseData.success(null);
  }

  @DeleteMapping("/{sessionId}/remove")
  public ApiResponseData<Void> removeMembers(@PathVariable UUID sessionId, @RequestBody @Valid RemoveMemberRequest request) {
    memberService.removeMember(request.toCommand(sessionId));
    return ApiResponseData.success(null);
  }


}
