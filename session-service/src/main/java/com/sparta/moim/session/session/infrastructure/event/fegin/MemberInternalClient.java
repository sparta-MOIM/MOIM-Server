package com.sparta.moim.session.session.infrastructure.event.fegin;

import com.sparta.moim.session.session.application.dto.result.GetSessionMemberListResult;
import com.sparta.moim.session.session.application.event.feigin.MemberInternalService;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-Client",url = "http://localhost:8089")
public interface MemberInternalClient extends MemberInternalService {

  @GetMapping(value = "/internal/v1/session/{sessionId}")
  List<GetSessionMemberListResult> getMembers(@PathVariable(value = "sessionId") UUID sessionId);

}
