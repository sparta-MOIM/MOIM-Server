package com.sparta.moim.session.member.infrastructure.event.feign;

import com.sparta.moim.session.member.application.event.feign.SessionInternalService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
@FeignClient(name = "session-Client",url = "${feign.client.member.url}")
public interface SessionInternalClient extends SessionInternalService {
  @PostMapping(value = "/internal/v1/session/{sessionId}/validate")
  void getSessionValidate(@PathVariable(value = "sessionId") UUID sessionId);

  @PostMapping(value = "/internal/v1/session/{sessionId}/validate/time")
  void getSessionValidateTime(@PathVariable(value = "sessionId") UUID sessionId);

  @PostMapping(value = "/internal/v1/session/{sessionId}/validate/status")
  void getSessionValidateOpenStatus(@PathVariable(value = "sessionId") UUID sessionId);
}
