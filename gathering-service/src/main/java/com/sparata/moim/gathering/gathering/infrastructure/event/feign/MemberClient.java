package com.sparata.moim.gathering.gathering.infrastructure.event.feign;

import com.sparata.moim.gathering.gathering.application.dto.result.GetGatheringMemberListResult;
import com.sparata.moim.gathering.gathering.application.event.feign.MemberService;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gathering-service",url = "http://localhost:19101/internal/v1/member")
public interface MemberClient extends MemberService {


  @GetMapping("/{gatheringId}")
  List<GetGatheringMemberListResult> findMembers(@PathVariable(name = "gatheringId") UUID gatheringId);

}
