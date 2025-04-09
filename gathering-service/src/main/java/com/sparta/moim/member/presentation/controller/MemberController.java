package com.sparta.moim.member.presentation.controller;

import com.sparta.moim.member.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gathering/member")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService recruitService;

  @PostMapping
  public void joinGathering() {
    recruitService.joinGathering();
  }

  @DeleteMapping
  public void leaveGathering() {
    recruitService.leaveGathering();
  }

  @DeleteMapping
  public void removeGathering() {
    recruitService.removeGathering();
  }





}
