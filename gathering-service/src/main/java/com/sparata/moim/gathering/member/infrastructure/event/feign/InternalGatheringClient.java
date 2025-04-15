package com.sparata.moim.gathering.member.infrastructure.event.feign;


import com.sparata.moim.gathering.member.application.event.feign.InternalGatheringService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gathering-Client",url = "http://localhost:19101/internal/v1")
public interface InternalGatheringClient extends InternalGatheringService {

  @PostMapping("/gathering/{gatheringId}/validate")
  void isExitsGathering(@PathVariable(name = "gatheringId") UUID gatheringId);

  @PostMapping("/gathering/{gatheringId}/status")
  void isGatheringStatusOpen(@PathVariable(name = "gatheringId") UUID gatheringId);
}
