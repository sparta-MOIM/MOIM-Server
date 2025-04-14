package com.sparata.moim.gathering.member.infrastructure.event.feign;


import com.sparata.moim.gathering.member.application.event.feign.GatheringService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gathering-service",url = "http://localhost:19101/internal/v1/gathering")
public interface GatheringClient extends GatheringService {

  @PostMapping("/{gatheringId}/validate")
  void isExitsGathering(@PathVariable(name = "gatheringId") UUID gatheringId);
}
