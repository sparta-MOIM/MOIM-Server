package com.sparta.moim.gathering.gathering.infrastructure.event.feign;

import com.sparta.moim.gathering.gathering.application.dto.result.GetGatheringMemberListResult;
import com.sparta.moim.gathering.gathering.application.event.feign.MemberService;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gathering-service",url = "${member.service.url}/internal/v1/member")
public interface MemberClient extends MemberService {


  /**
   * 지정된 모임 ID에 해당하는 모든 멤버 정보를 조회합니다.
   *
   * @param gatheringId 멤버 목록을 조회할 모임의 고유 ID
   * @return 해당 모임에 속한 멤버 정보 목록
   */
  @GetMapping("/{gatheringId}")
  List<GetGatheringMemberListResult> findMembers(@PathVariable(name = "gatheringId") UUID gatheringId);

}
