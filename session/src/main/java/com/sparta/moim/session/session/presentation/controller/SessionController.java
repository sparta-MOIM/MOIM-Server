package com.sparta.moim.session.session.presentation.controller;

import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.session.application.service.SessionService;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateStateRequest;
import com.sparta.moim.session.session.presentation.dto.response.CreateSessionResponse;
import com.sparta.moim.session.session.presentation.dto.response.GetSessionResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
  private final SessionService sessionService;

  @PostMapping
  public CreateSessionResponse createSession(@RequestBody CreateSessionRequest request,
                                             @AuthenticationPrincipal CustomUserDetails details) {
    return CreateSessionResponse.create(sessionService.createSession(request.toCommand(details.getUsername(), details.getRole())));
  }


  @GetMapping("/{sessionId}")
  public GetSessionResponse getSession(@PathVariable UUID sessionId) {
    return GetSessionResponse.get(sessionService.getSession(sessionId));
  }

  @GetMapping
  public void searchSession() {
    sessionService.searchSession();
  }


  @PutMapping("/{sessionId}")
  public void updateSession(@PathVariable UUID sessionId, @RequestBody UpdateSessionRequest request) {
    sessionService.updateSession(request.toCommand(sessionId));
  }

  @PatchMapping("/{sessionId}/state")
  public void updateStateSession(@PathVariable UUID sessionId, @RequestBody UpdateStateRequest request) {
    sessionService.statusUpdateSession(request.toCommand(sessionId));
  }

  @DeleteMapping("/{sessionId}")
  public void deleteSession(@PathVariable String sessionId) {
    sessionService.deleteSession();
  }

  @PatchMapping("/{sessionId}/apply")
  public void applySession(@PathVariable String sessionId) {
    sessionService.applySession();
  }


}
