package com.sparta.moim.gathering.gathering.presentation.contoller.Internal;

import com.sparta.moim.gathering.gathering.application.service.GatheringValidationService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/gathering")
@RequiredArgsConstructor
public class ValidateGatheringController {
  private final GatheringValidationService gatheringValidationService;


  /**
   * 주어진 모임 ID에 해당하는 모임이 존재하는지 검증합니다.
   *
   * @param gatheringId 존재 여부를 확인할 모임의 UUID
   */
  @PostMapping("/{gatheringId}/validate")
  public void existsGathering(@PathVariable UUID gatheringId) {
    gatheringValidationService.existsGathering(gatheringId);
  } 
  
  /**
   * 주어진 모임 ID에 해당하는 모임이 존재하며, 현재 오픈 상태인지 검증합니다.
   *
   * @param gatheringId 검증할 모임의 UUID
   */
  @PostMapping("/{gatheringId}/status")
  public void exitsOpenStatusGathering(@PathVariable UUID gatheringId) {
    gatheringValidationService.existsOpenStatusGathering(gatheringId);
  }

}
