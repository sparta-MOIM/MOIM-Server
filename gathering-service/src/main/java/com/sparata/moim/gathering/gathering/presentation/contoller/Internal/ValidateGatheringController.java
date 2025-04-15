package com.sparata.moim.gathering.gathering.presentation.contoller.Internal;

import com.sparata.moim.gathering.gathering.application.service.GatheringValidationService;
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


  @PostMapping("/{gatheringId}/validate")
  public void exitsGathering(@PathVariable UUID gatheringId) {
    gatheringValidationService.exitsGathering(gatheringId);
  } 
  
  @PostMapping("/{gatheringId}/status")
  public void exitsOpenStatusGathering(@PathVariable UUID gatheringId) {
    gatheringValidationService.exitsOpenStatusGathering(gatheringId);
  }

}
