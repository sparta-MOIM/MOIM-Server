package com.sparta.moim.gathering.gathering.infrastructure.event.feign;


import com.sparta.moim.gathering.gathering.application.event.feign.InternalGatheringService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gathering-Client",url = "${member.service.url}/internal/v1")
public interface InternalGatheringClient extends InternalGatheringService {

  @PostMapping("/gathering/{gatheringId}/validate")
  void validateGatheringExists(@PathVariable(name = "gatheringId") UUID gatheringId);

  @PostMapping("/gathering/{gatheringId}/status")
  void validateGatheringStatusOpen(@PathVariable(name = "gatheringId") UUID gatheringId);
}
