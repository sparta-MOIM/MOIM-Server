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
import com.sparta.moim.gathering.gathering.application.event.feign.MemberService;
import com.sparta.moim.gathering.gathering.application.event.publisher.AddMemberPublisher;
import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepository;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepositoryCustom;
import com.sparta.moim.gathering.shared.error.code.GatheringCode;
import com.sparta.moim.gathering.shared.error.exception.GatheringException;
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
  private final MemberService memberService;
  private final AddMemberPublisher addMemberPublisher;

  /**
   * 새로운 모임을 생성하고, 소유자를 멤버로 추가합니다.
   *
   * 모임 이름이 이미 존재하는 경우 예외가 발생합니다.
   *
   * @param command 모임 생성에 필요한 정보가 담긴 명령 객체
   * @return 생성된 모임의 정보를 담은 결과 객체
   * @throws GatheringException 모임 이름이 중복될 경우 발생
   */
  public CreateGatheringResult createGathering(CreateGatheringCommand command) {
    if (gatheringRepository.existsByNameAndDeletedAtIsNull(command.name())) {
      throw new GatheringException(GatheringCode.EXISTS_NAME_GATHERING);
    }
    Gathering savedGathering = gatheringRepository.save(command.toEntity());
    addMemberPublisher.add(savedGathering.getTrackingId(), savedGathering.getOwner());
    return CreateGatheringResult.create(savedGathering);
  }

  /**
   * 주어진 명령을 기반으로 모임 정보를 수정합니다.
   *
   * 동일한 이름의 다른 모임이 존재할 경우 예외를 발생시키며, 존재하지 않는 모임 ID가 주어지면 예외를 발생시킵니다.
   *
   * @param command 수정할 모임의 정보가 담긴 명령 객체
   * @throws GatheringException 중복된 모임 이름이 있거나, 모임을 찾을 수 없는 경우
   */
  @Transactional
  public void updateGathering(UpdateGatheringCommand command) {
    UUID id = command.gatheringId();
    if (duplicateGatheringName(command.name(), id)) {
      throw new GatheringException(GatheringCode.EXISTS_NAME_GATHERING);
    }

    Gathering gathering = gatheringRepository.findByTrackingIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    gathering.change(command.toDomain());
  }

  /**
   * 주어진 이름을 가진 모임이 삭제되지 않았으며 지정된 ID와 다른 경우 중복 여부를 확인합니다.
   *
   * @param name 확인할 모임 이름
   * @param id   제외할 모임의 ID
   * @return 중복된 모임 이름이 존재하면 true, 아니면 false
   */
  private boolean duplicateGatheringName(String name, UUID id) {
    if (name == null) {
      return false;
    }
    return gatheringRepository.existsByNameAndDeletedAtIsNullAndTrackingIdNot(name, id);
  }

  /**
   * 주어진 모임 ID에 해당하는 모임 정보와 멤버 목록을 조회합니다.
   *
   * @param gatheringId 조회할 모임의 고유 식별자
   * @return 모임 정보와 멤버 목록이 포함된 결과 객체
   * @throws GatheringException 해당 모임이 존재하지 않을 경우 발생
   */
  @Transactional(readOnly = true)
  public GetGatheringResult getGathering(UUID gatheringId) {
    List<GetGatheringMemberListResult> members = memberService.findMembers(gatheringId);
    Gathering gathering = gatheringRepository.findByTrackingIdAndDeletedAtIsNull(gatheringId)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    return GetGatheringResult.get(gathering, members);
  }

  /**
   * 주어진 명령에 따라 모임을 소프트 삭제합니다.
   *
   * @param command 삭제할 모임의 식별자와 요청자 정보를 포함한 명령 객체
   * @throws GatheringException 해당 모임이 존재하지 않을 경우 발생
   */
  @Transactional
  public void deleteGathering(DeleteGatheringCommand command) {
    UUID gatheringId = command.gatheringId();
    Gathering gathering = gatheringRepository.findByTrackingIdAndDeletedAtIsNull(gatheringId)
        .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING));
    gathering.softDelete(command.username());
  }

  /**
   * 주어진 검색 조건에 따라 모임 목록을 페이징하여 조회합니다.
   *
   * @param command 모임 검색 조건 및 페이징 정보
   * @return 검색된 모임 요약 목록과 전체 개수, 현재 페이지, 페이지 크기를 포함한 결과
   */
  @Transactional(readOnly = true)
  public SearchGatheringResult searchGathering(SearchGatheringCommand command) {
    Pagination<Gathering> gatherings = gatheringRepositoryCustom.searchGathering(command.toCriteria());
    return SearchGatheringResult.search(gatherings.getContent().stream().map(SearchGatheringListResult::new).toList(),
        gatherings.getTotal(), gatherings.getPage(), gatherings.getContent().size());
  }
}
