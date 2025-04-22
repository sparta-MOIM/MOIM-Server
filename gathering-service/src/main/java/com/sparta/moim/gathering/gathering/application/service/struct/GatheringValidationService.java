package com.sparta.moim.gathering.gathering.application.service.struct;

import com.sparta.moim.gathering.gathering.application.exception.NotFoundGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotOpenGatheringException;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringValidationService {
  private final GatheringValidationRepository gatheringRepository;

  public void existsGathering(UUID gatheringId) {
    if (!gatheringRepository.existsByTrackingIdAndDeletedAtNull(gatheringId)) {
      throw new NotFoundGatheringException();
    }
  }

  public void existsOpenStatusGathering(UUID gatheringId) {
    boolean isOpen = gatheringRepository.isGatheringOpen(gatheringId);

    if (!isOpen) {
      throw new NotOpenGatheringException();
    }
  }
}
