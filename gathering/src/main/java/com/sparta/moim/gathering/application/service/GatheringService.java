package com.sparta.moim.gathering.application.service;

import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import com.sparta.moim.gathering.application.dto.command.UpdateGatheringCommand;
import com.sparta.moim.gathering.application.dto.query.CreateGatheringQuery;
import com.sparta.moim.gathering.application.dto.query.GetGatheringQuery;
import com.sparta.moim.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.domain.entity.repository.GatheringRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringService {
  private final GatheringRepository gatheringRepository;

  public CreateGatheringQuery createGathering(CreateGatheringCommand command) {
    return CreateGatheringQuery.create(gatheringRepository.save(command.toEntity()));
  }

  @Transactional
  public void updateGathering(UpdateGatheringCommand command) {
    UUID id = command.gatheringId();
    Gathering gathering = gatheringRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + id + " not found"));
    gathering.change(command.toDomain());
  }

  @Transactional(readOnly = true)
  public GetGatheringQuery getGathering(UUID gatheringId) {
    Gathering gathering = gatheringRepository.findById(gatheringId)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + gatheringId + " not found"));
    return GetGatheringQuery.get(gathering);
  }
}
