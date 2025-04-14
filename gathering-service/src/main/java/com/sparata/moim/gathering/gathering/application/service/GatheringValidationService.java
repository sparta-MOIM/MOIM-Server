package com.sparata.moim.gathering.gathering.application.service;

import com.sparata.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import com.sparata.moim.gathering.shared.error.code.GatheringCode;
import com.sparata.moim.gathering.shared.error.exception.GatheringException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringValidationService {
  private final GatheringValidationRepository gatheringRepository;

  public void exitsGathering(UUID gatheringId) {
    if (!gatheringRepository.existsByTrackingIdAndDeletedAtNull(gatheringId)) {
      throw new GatheringException(GatheringCode.NOT_FOUND_GATHERING);
    }
  }
}
