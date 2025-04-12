package com.sparta.moim.session.session.presentation.controller.internal;

import com.sparta.moim.session.session.application.service.SessionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/session")
@RequiredArgsConstructor
public class ValidationSessionController {
  private final SessionService sessionService;

  @PostMapping("/{sessionId}/validate")
  public void isValidateSession(@PathVariable UUID sessionId) {
    sessionService.isValidateSession(sessionId);
  }

  @PostMapping("/{sessionId}/validate/status")
  public void isValidateSessionStatus(@PathVariable UUID sessionId) {
    sessionService.isValidateSessionStatus(sessionId);
  }

  @PostMapping("/{sessionId}/validate/time")
  public void isValidateSessionTimeCheck(@PathVariable UUID sessionId) {
    sessionService.isValidateTimeSession(sessionId);
  }
}
