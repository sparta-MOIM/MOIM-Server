package com.sparta.moim.gathering.gathering.application.service.struct;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.response.CommonCode;
import com.sparta.moim.gathering.gathering.application.dto.command.event.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.event.feigin.OrganizationService;
import com.sparta.moim.gathering.gathering.application.exception.AlreadyParticipateFoundGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotConnectedGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotFoundGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotFoundGatheringMemberException;
import com.sparta.moim.gathering.gathering.application.exception.NotJoinGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotOpenGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.RoleNotAllowedGatheringException;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringRepository;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import com.sparta.moim.gathering.shared.enums.MemberType;
import com.sparta.moim.gathering.shared.enums.OrganizationMemberRole;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceStruct {
  private final MemberRepository memberRepository;
  private final GatheringRepository gatheringRepository;
  private final GatheringValidationRepository gatheringValidationRepository;
  private final OrganizationService organizationService;

  public void joinGathering(JoinGatheringCommand command) {
    checkRole(command.gatheringId(), command.userId());

    validateGatheringExists(command.gatheringId());
    statusTrueValidate(command);

    if (memberRepository.existsByGatheringIdAndMemberId(command.gatheringId(), command.userId())) {
      throw new AlreadyParticipateFoundGatheringException();
    }

  }

  private void checkRole(UUID gatheringId, UUID userId) {
    UUID organizationId = UUID.fromString(gatheringRepository.findOrganizationId(gatheringId));

    List<OrganizationMemberRole> allRole = List.of(OrganizationMemberRole.MANAGER,
        OrganizationMemberRole.MANAGER,
        OrganizationMemberRole.MEMBER);
    ApiResponseData<Boolean> check = organizationService.checkRole(organizationId, userId, allRole);
    // 200이 발생하지 않는 다면 에러를 리턴한다.
    if (!Objects.equals(check.getCode(), CommonCode.SUCCESS.getCode())) {
      throw new NotConnectedGatheringException();
    }

    if (!check.getData()) {
      throw new NotJoinGatheringException();
    }

  }

  private void statusTrueValidate(JoinGatheringCommand command) {
    if (!gatheringValidationRepository.isGatheringOpen(command.gatheringId())) {
      throw new NotOpenGatheringException();
    }
  }

  public void leaveGathering(LeaveGatheringCommand command) {
    validateGatheringExists(command.gatheringId());

    UUID gatheringId = command.gatheringId();
    UUID userId = command.userId();

    if (!memberRepository.existsByGatheringIdAndMemberId(gatheringId, userId)) {
      throw new NotFoundGatheringMemberException();
    }


  }

  @Transactional
  public void removeGathering(RemoveGatheringCommand command) {
    MemberType memberType = memberRepository.findMemberType(command.gatheringId(), command.memberId());

    if (memberType == MemberType.GENERAL) {
      throw new RoleNotAllowedGatheringException();
    }

    validateGatheringExists(command.gatheringId());
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId(), command.users());
  }

  private void validateGatheringExists(UUID id) {
    if (!gatheringValidationRepository.existsByTrackingIdAndDeletedAtNull(id)) {
      throw new NotFoundGatheringException();
    }
  }
}
