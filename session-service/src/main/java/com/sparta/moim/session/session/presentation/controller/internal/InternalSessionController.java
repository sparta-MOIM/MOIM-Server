package com.sparta.moim.session.session.presentation.controller.internal;

import com.sparta.moim.session.session.application.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/session")
@RequiredArgsConstructor
public class InternalSessionController {
  private final SessionService sessionService;


}
