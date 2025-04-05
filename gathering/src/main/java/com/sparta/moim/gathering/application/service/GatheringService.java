package com.sparta.moim.gathering.application.service;

import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import com.sparta.moim.gathering.application.dto.query.CreateGatheringQuery;
import com.sparta.moim.gathering.domain.entity.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringService {
  private final GatheringRepository gatheringRepository;

  public CreateGatheringQuery createGathering(CreateGatheringCommand command) {
    return CreateGatheringQuery.create(gatheringRepository.save(command.toEntity()));
  }
}
