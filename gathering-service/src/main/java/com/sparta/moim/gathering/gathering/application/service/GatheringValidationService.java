package com.sparta.moim.gathering.gathering.application.service;

import com.sparta.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import com.sparta.moim.gathering.shared.error.code.GatheringCode;
import com.sparta.moim.gathering.shared.error.exception.GatheringException;
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

  public void existsOpenStatusGathering(UUID gatheringId) {
    boolean status = gatheringRepository.isGatheringOpen(gatheringId);

    if (!status) {
      throw new GatheringException(GatheringCode.NOT_OPEN_GATHERING);
    }
  }
}
