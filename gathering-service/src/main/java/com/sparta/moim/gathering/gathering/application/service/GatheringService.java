package com.sparta.moim.gathering.gathering.application.service;

import com.sparta.moim.gathering.gathering.application.dto.command.CreateGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.DeleteGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.UpdateGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.result.CreateGatheringResult;
import com.sparta.moim.gathering.gathering.application.dto.result.GetGatheringResult;
import com.sparta.moim.gathering.gathering.application.dto.result.GetGatheringMemberListResult;
import com.sparta.moim.gathering.gathering.application.dto.result.SearchGatheringListResult;
import com.sparta.moim.gathering.gathering.application.dto.result.SearchGatheringResult;
import com.sparta.moim.gathering.gathering.application.event.publisher.MemberPublisher;
import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepository;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepositoryCustom;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import com.sparta.moim.gathering.gathering.application.code.GatheringCode;
import com.sparta.moim.gathering.gathering.application.exception.GatheringException;
import com.sparta.moim.common.page.Pagination;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringService {
  private final GatheringRepository gatheringRepository;
  private final GatheringRepositoryCustom gatheringRepositoryCustom;

  private final MemberRepository memberRepository;

  private final MemberPublisher memberPublisher;


  public CreateGatheringResult createGathering(CreateGatheringCommand command) {
    if (gatheringRepository.existsByNameAndDeletedAtIsNull(command.name())) {
      throw new GatheringException(GatheringCode.EXISTS_NAME_GATHERING);
    }
    Gathering savedGathering = gatheringRepository.save(command.toEntity());
    memberPublisher.add(savedGathering.getTrackingId(), savedGathering.getOwner());
    return CreateGatheringResult.create(savedGathering);
  }

  @Transactional
  public void updateGathering(UpdateGatheringCommand command) {
    UUID id = command.gatheringId();
    if (duplicateGatheringName(command.name(), id)) {
      throw new GatheringException(GatheringCode.EXISTS_NAME_GATHERING);
    }

    Gathering gathering = gatheringRepository.findByTrackingIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    gathering.change(command.toDomain());

    if (command.owner() != null) {
      memberPublisher.revoke(id, command.owner());
    }
  }

  private boolean duplicateGatheringName(String name, UUID id) {
    if (name == null) {
      return false;
    }
    return gatheringRepository.existsByNameAndDeletedAtIsNullAndTrackingIdNot(name, id);
  }

  @Transactional(readOnly = true)
  public GetGatheringResult getGathering(UUID gatheringId) {
    List<GetGatheringMemberListResult> members = memberRepository.findMembers(gatheringId).stream().map(g -> new GetGatheringMemberListResult(g)).toList();
    Gathering gathering = gatheringRepository.findByTrackingIdAndDeletedAtIsNull(gatheringId)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    return GetGatheringResult.get(gathering, members);
  }

  @Transactional
  public void deleteGathering(DeleteGatheringCommand command) {
    UUID gatheringId = command.gatheringId();
    Gathering gathering = gatheringRepository.findByTrackingIdAndDeletedAtIsNull(gatheringId)
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
