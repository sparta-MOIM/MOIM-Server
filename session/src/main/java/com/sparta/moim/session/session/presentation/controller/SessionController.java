package com.sparta.moim.session.session.presentation.controller;

import com.sparta.moim.session.session.application.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
  private final SessionService sessionService;
  @PostMapping
  public void createSession() {
    sessionService.createSession();
  }


  @GetMapping("/{sessionId}")
  public void getSession(@PathVariable String sessionId) {
    sessionService.getSession();
  }

  @GetMapping
  public void searchSession() {
    sessionService.searchSession();
  }


  @PutMapping("/{sessionId}")
  public void updateSession() {
    sessionService.updateSession();
  }

  @PatchMapping("/{sessionId}")
  public void statusUpdateSession() {
    sessionService.statusUpdateSession();
  }

  @DeleteMapping("/{sessionId}")
  public void deleteSession() {
    sessionService.deleteSession();
  }

  @PatchMapping("/{sessionId}/apply")
  public void applySession() {
    sessionService.applySession();
  }



}
