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

  /**
   * 주어진 모임 ID에 해당하는 모임이 존재하며 삭제되지 않았는지 검증합니다.
   *
   * 존재하지 않거나 삭제된 모임일 경우 {@code GatheringException}이 발생합니다.
   *
   * @param gatheringId 검증할 모임의 UUID
   * @throws GatheringException 모임이 존재하지 않거나 삭제된 경우
   */
  public void existsGathering(UUID gatheringId) {
    if (!gatheringRepository.existsByTrackingIdAndDeletedAtNull(gatheringId)) {
      throw new GatheringException(GatheringCode.NOT_FOUND_GATHERING);
    }
  }

  /**
   * 지정된 모임이 현재 오픈 상태인지 검증합니다.
   *
   * 모임이 오픈 상태가 아니면 {@code GatheringException}을 발생시킵니다.
   *
   * @param gatheringId 검증할 모임의 UUID
   * @throws GatheringException 모임이 오픈 상태가 아닌 경우 발생
   */
  public void existsOpenStatusGathering(UUID gatheringId) {
    boolean isOpen = gatheringRepository.isGatheringOpen(gatheringId);

    if (!isOpen) {
      throw new GatheringException(GatheringCode.NOT_OPEN_GATHERING);
    }
  }
}
