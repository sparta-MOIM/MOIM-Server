package com.sparta.moim.session.member.application.event.feign;

import java.util.UUID;

public interface SessionInternalService {
  void getSessionValidate(UUID sessionId);
  void getSessionValidateTime(UUID sessionId);
  void getSessionValidateOpenStatus(UUID sessionId);
}
