package com.sparata.moim.gathering.gathering.application.service;

import com.sparata.moim.gathering.shared.error.code.GatheringCode;
import com.sparata.moim.gathering.shared.error.exception.GatheringException;
import com.sparta.moim.common.page.Pagination;
import com.sparata.moim.gathering.gathering.application.dto.command.CreateGatheringCommand;
import com.sparata.moim.gathering.gathering.application.dto.command.DeleteGatheringCommand;
import com.sparata.moim.gathering.gathering.application.dto.command.SearchGatheringCommand;
import com.sparata.moim.gathering.gathering.application.dto.command.UpdateGatheringCommand;
import com.sparata.moim.gathering.gathering.application.dto.result.CreateGatheringResult;
import com.sparata.moim.gathering.gathering.application.dto.result.GetGatheringResult;
import com.sparata.moim.gathering.gathering.application.dto.result.SearchGatheringListResult;
import com.sparata.moim.gathering.gathering.application.dto.result.SearchGatheringResult;
import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import com.sparata.moim.gathering.gathering.domain.repository.GatheringRepository;
import com.sparata.moim.gathering.gathering.domain.repository.GatheringRepositoryCustom;
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
    return CreateGatheringResult.create(gatheringRepository.save(command.toEntity()));
  }

  @Transactional
  public void updateGathering(UpdateGatheringCommand command) {
    UUID id = command.gatheringId();
    Gathering gathering = gatheringRepository.findByTrackingId(id)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    gathering.change(command.toDomain());
  }

  @Transactional(readOnly = true)
  public GetGatheringResult getGathering(UUID gatheringId) {
    Gathering gathering = gatheringRepository.findByTrackingId(gatheringId)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    return GetGatheringResult.get(gathering);
  }

  @Transactional
  public void deleteGathering(DeleteGatheringCommand command) {
    UUID gatheringId = command.gatheringId();
    Gathering gathering = gatheringRepository.findByTrackingId(gatheringId)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    gathering.softDelete(command.username());
  }

  @Transactional(readOnly = true)
  public SearchGatheringResult searchGathering(SearchGatheringCommand command) {
    Pagination<Gathering> gatherings = gatheringRepositoryCustom.searchGathering(command.toCriteria());
    return SearchGatheringResult.search(gatherings.getContent().stream().map(SearchGatheringListResult::new).toList(),
        gatherings.getTotal(), gatherings.getPage(), gatherings.getContent().size());
  }
}
