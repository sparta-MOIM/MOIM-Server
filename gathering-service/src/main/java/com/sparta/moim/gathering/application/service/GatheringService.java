package com.sparta.moim.gathering.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import com.sparta.moim.gathering.application.dto.command.DeleteGatheringCommand;
import com.sparta.moim.gathering.application.dto.command.SearchGatheringCommand;
import com.sparta.moim.gathering.application.dto.command.UpdateGatheringCommand;
import com.sparta.moim.gathering.application.dto.query.CreateGatheringQuery;
import com.sparta.moim.gathering.application.dto.query.GetGatheringQuery;
import com.sparta.moim.gathering.application.dto.query.SearchGatheringListQuery;
import com.sparta.moim.gathering.application.dto.query.SearchGatheringQuery;
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

  public CreateGatheringQuery createGathering(CreateGatheringCommand command) {
    return CreateGatheringQuery.create(gatheringRepository.save(command.toEntity()));
  }

  @Transactional
  public void updateGathering(UpdateGatheringCommand command) {
    Long id = command.gatheringId();
    Gathering gathering = gatheringRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + id + " not found"));
    gathering.change(command.toDomain());
  }

  @Transactional(readOnly = true)
  public GetGatheringQuery getGathering(Long gatheringId) {
    Gathering gathering = gatheringRepository.findById(gatheringId)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + gatheringId + " not found"));
    return GetGatheringQuery.get(gathering);
  }

  @Transactional
  public void deleteGathering(DeleteGatheringCommand command) {
    Long gatheringId = command.gatheringId();
    Gathering gathering = gatheringRepository.findById(gatheringId)
        .orElseThrow(() -> new IllegalArgumentException("Gathering with id " + gatheringId + " not found"));
    gathering.softDelete(command.username());
  }

  @Transactional(readOnly = true)
  public SearchGatheringQuery searchGathering(SearchGatheringCommand command) {
    Pagination<Gathering> gatherings = gatheringRepositoryCustom.searchGathering(command.toCriteria());
    return SearchGatheringQuery.search(gatherings.getContent().stream().map(SearchGatheringListQuery::new).toList(),
        gatherings.getTotal(), gatherings.getPage(), gatherings.getContent().size());
  }
}
