package com.sparta.moim.gathering.member.infrastructure.event.feign;


import com.sparta.moim.gathering.member.application.event.feign.InternalGatheringService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gathering-Client",url = "${member.service.url}/internal/v1")
public interface InternalGatheringClient extends InternalGatheringService {

  /**
   * 지정된 모임 ID에 해당하는 모임이 존재하는지 원격 서비스에서 검증합니다.
   *
   * @param gatheringId 존재 여부를 확인할 모임의 UUID
   */
  @PostMapping("/gathering/{gatheringId}/validate")
  void validateGatheringExists(@PathVariable(name = "gatheringId") UUID gatheringId);

  /**
   * 주어진 모임 ID에 해당하는 모임의 상태가 '오픈' 상태인지 원격 서비스에서 검증합니다.
   *
   * @param gatheringId 상태를 확인할 모임의 고유 식별자
   */
  @PostMapping("/gathering/{gatheringId}/status")
  void validateGatheringStatusOpen(@PathVariable(name = "gatheringId") UUID gatheringId);
}
