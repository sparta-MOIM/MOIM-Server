package com.sparta.moim.gathering.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import com.sparta.moim.gathering.application.dto.command.DeleteGatheringCommand;
import com.sparta.moim.gathering.application.dto.command.SearchGatheringCommand;
import com.sparta.moim.gathering.application.dto.command.UpdateGatheringCommand;
import com.sparta.moim.gathering.application.dto.result.CreateGatheringResult;
import com.sparta.moim.gathering.application.dto.result.GetGatheringResult;
import com.sparta.moim.gathering.application.dto.result.SearchGatheringListResult;
import com.sparta.moim.gathering.application.dto.result.SearchGatheringResult;
import com.sparta.moim.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.domain.repository.GatheringRepository;
import com.sparta.moim.gathering.domain.repository.GatheringRepositoryCustom;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringService {
  private final GatheringRepository gatheringRepository;
  private final GatheringRepositoryCustom gatheringRepositoryCustom;

  public CreateGatheringResult createGathering(CreateGatheringCommand command) {
    if (gatheringRepository.existsByNameAndDeletedByIsNull(command.name())) {
      throw new IllegalArgumentException("Gathering name already exists");
    }
    return CreateGatheringResult.create(gatheringRepository.save(command.toEntity()));
  }

  @Transactional
  public void updateGathering(UpdateGatheringCommand command) {
    UUID id = command.gatheringId();
    if (gatheringRepository.existsByNameAndDeletedByIsNullAndIdNot(command.name(), command.gatheringId())) {
      throw new IllegalArgumentException("Gathering name already exists");
    }
    Gathering gathering = gatheringRepository.findByTrackingId(id)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + id + " not found"));
    gathering.change(command.toDomain());
  }

  @Transactional(readOnly = true)
  public GetGatheringResult getGathering(UUID gatheringId) {
    Gathering gathering = gatheringRepository.findByTrackingId(gatheringId)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + gatheringId + " not found"));
    return GetGatheringResult.get(gathering);
  }

  @Transactional
  public void deleteGathering(DeleteGatheringCommand command) {
    UUID gatheringId = command.gatheringId();
    Gathering gathering = gatheringRepository.findByTrackingId(gatheringId)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + gatheringId + " not found"));
    gathering.softDelete(command.username());
  }

  @Transactional(readOnly = true)
  public SearchGatheringResult searchGathering(SearchGatheringCommand command) {
    Pagination<Gathering> gatherings = gatheringRepositoryCustom.searchGathering(command.toCriteria());
    return SearchGatheringResult.search(gatherings.getContent().stream().map(SearchGatheringListResult::new).toList(),
        gatherings.getTotal(), gatherings.getPage(), gatherings.getContent().size());
  }
}
